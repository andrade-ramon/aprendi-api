package com.qualfacul.hades.task;
import static java.lang.Long.parseLong;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.qualfacul.hades.annotation.TaskComponent;
import com.qualfacul.hades.college.College;
import com.qualfacul.hades.college.CollegeAddress;
import com.qualfacul.hades.college.CollegeAddressRepository;
import com.qualfacul.hades.college.CollegeRepository;
import com.qualfacul.hades.course.Course;
import com.qualfacul.hades.course.CourseGrade;
import com.qualfacul.hades.course.CourseGradeRepository;
import com.qualfacul.hades.course.CourseModality;
import com.qualfacul.hades.course.CourseRepository;
import com.qualfacul.hades.mec.MecCollegeService;
import com.qualfacul.hades.mec.MecCourseService;
import com.qualfacul.hades.mec.MecRequester;

@TaskComponent
class MECInformationsTasks {

	private static final Logger LOGGER = LoggerFactory.getLogger(MECInformationsTasks.class);

	@Autowired
	private CollegeRepository collegeRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private CourseGradeRepository courseGradeRepository;
	@Autowired
	private CollegeAddressRepository collegeAddressRepository;
	@Autowired
	private MecCollegeService mecCollegeService;
	@Autowired
	private MecCourseService mecCourseService;
	@Autowired
	private MecRequester mecRequester;

//	@Scheduled(cron = "* 0 0 1 * ?")
//	@Scheduled(fixedDelay = 10_000_000)
	void getAllColleges() {
		LOGGER.info("STARTING TASK GETALLCOLLEGES");

		ExecutorService executor = Executors.newFixedThreadPool(80);

		Document collegeList = Jsoup.parse(mecRequester.requestCollegeList());
		int totalPages = Integer.parseInt(
				collegeList.select("select#paginationControlItemdiv_listar_consulta_avancada option").last().text());
		int page = 2;
		do {
			LOGGER.info("REQUESTING PAGE " + (page-1) + " OF " + totalPages);

			collegeList.select("tbody#tbyDados > tr").forEach(tr -> {
				tr.children().last().remove();
				String id = tr.attr("onclick");
				Long mecId = parseLong(id.subSequence(id.indexOf("(") + 1, id.indexOf(")")).toString().trim());

				Runnable collegeRequester = new CollegeRequester(mecId, mecRequester, mecCollegeService,
						collegeRepository);
				executor.execute(collegeRequester);
			});

			collegeList = Jsoup.parse(mecRequester.requestCollegeList(page++));
		} while (page <= totalPages);

		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		LOGGER.info("FINISHED TASK GETALLCOLLEGES");
	}

	private static class CollegeRequester implements Runnable {
		private Long mecId;
		private MecRequester mecRequester;
		private MecCollegeService mecCollegeService;
		private CollegeRepository collegeRepository;

		CollegeRequester(Long mecId, MecRequester mecRequester, MecCollegeService mecCollegeService,
				CollegeRepository collegeRepository) {
			this.mecId = mecId;
			this.mecRequester = mecRequester;
			this.mecCollegeService = mecCollegeService;
			this.collegeRepository = collegeRepository;
		}

		@Override
		public void run() {
			Document collegeDetails = Jsoup.parse(mecRequester.requestCollegeDetails(mecId.toString()));
			College college = mecCollegeService.setupBasicCollegeInformations(collegeDetails);
			college.setMecId(mecId);
			collegeRepository.save(college);
			LOGGER.info("SAVED COLLEGE: {}", mecId);
		}
	}
	
	@Scheduled(fixedDelay = 10_000_000)
//	@Scheduled(cron = "* 0 0 1 * ?")
	void getAllCourses() {
		LOGGER.info("STARTING TASK GETALLCOURSES");
			
		Document courseListDocument = Jsoup.parse(mecRequester.requestCoursesList());
		int totalPages = Integer.parseInt(courseListDocument
				.select("select#paginationControlItemdiv_listar_consulta_avancada option").last().text());
		
		ExecutorService executor = Executors.newFixedThreadPool(10);		
		int page = 1;
		do {
			Runnable courseRequester = new CourseRequester(mecRequester, collegeRepository, 
					mecCourseService, courseRepository, collegeAddressRepository, courseGradeRepository, page++);
			executor.execute(courseRequester);
		} while (page <= totalPages);
		
		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		
		LOGGER.info("FINISHED TASK GETALLCOURSES");
	}
	
	private static class CourseRequester implements Runnable {
		private MecRequester mecRequester;
		private CollegeRepository collegeRepository;
		private MecCourseService mecCourseService;
		private CourseRepository courseRepository;
		private int page;
		private CourseGradeRepository courseGradeRepository;
		
		CourseRequester(MecRequester mecRequester, CollegeRepository collegeRepository,
				MecCourseService mecCourseService, CourseRepository courseRepository,
				CollegeAddressRepository collegeAddressRepository, CourseGradeRepository courseGradeRepository, int page) {
			this.mecRequester = mecRequester;
			this.collegeRepository = collegeRepository;
			this.mecCourseService = mecCourseService;
			this.courseRepository = courseRepository;
			this.courseGradeRepository = courseGradeRepository;
			this.page = page;
		}
		
		@Override
		public void run() {
			LOGGER.info("REQUESTING PAGE {}", page);
			Document courseList = Jsoup.parse(mecRequester.requestCoursesList(this.page));
			courseList.select("table#tbDataGridNova #tbyDados tr").forEach(tr -> {
				Elements tds = tr.select("td");

				String fn = tds.get(0).text();
				String collegeMecIdParameter = (fn.subSequence(fn.indexOf("(") + 1, fn.indexOf(")"))).toString().trim();
				Long collegeMecId = parseLong(collegeMecIdParameter.split(",")[0].trim());
				
				Optional<College> optionalCollege = collegeRepository.findByMecId(collegeMecId);
				
				if (!optionalCollege.isPresent()) {
					LOGGER.error("COLLEGE NOT FOUND WITH MEC ID: {}", collegeMecId.toString());
				} else {
					College college = optionalCollege.get();
					Course course = mecCourseService.setupCourseInfo(tds);
					if(course.getModality().equals(CourseModality.PRESENTIAL)) {
						Optional<Course> optionalCourse = courseRepository.findByNameAndDegreeAndModality(course.getName(),
								course.getDegree(), course.getModality());
	
						if (!optionalCourse.isPresent()) {
							courseRepository.save(course);
							LOGGER.info("SAVED COURSE: {}", course.getId());
						} else {
							course = optionalCourse.get();
							LOGGER.info("COURSE ALREADY SAVED: {}", course.getId());
						}
						String courseName = tds.get(1).text().trim();
						String courseMecId = courseName.split("\\)")[0].trim().substring(1);
						
						Document courseAddress = Jsoup.parse(mecRequester.requestCourseAddress(courseMecId));
						Element trAddress = courseAddress.select("table#listar-ies-cadastro tbody tr").get(0);
						
						CollegeAddress collegeAddress = mecCourseService.setupCourseAddress(trAddress, college, course);
						
						List<CourseGrade> courseGrades = mecCourseService.setupCourseGrades(tds, course, collegeAddress);
						courseGrades.forEach(c -> courseGradeRepository.save(c));
					}
				}
			});	
		}
	}
}