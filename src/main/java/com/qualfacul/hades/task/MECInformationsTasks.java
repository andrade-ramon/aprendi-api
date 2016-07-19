package com.qualfacul.hades.task;

import static java.lang.Integer.parseInt;
import static java.lang.Long.parseLong;

import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
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
import com.qualfacul.hades.course.CourseRepository;
import com.qualfacul.hades.mec.MecCollegeService;
import com.qualfacul.hades.mec.MecCourseService;
//import com.qualfacul.hades.mec.MecCourseService;
import com.qualfacul.hades.mec.MecRequester;

@TaskComponent
class MECInformationsTasks {

	private static final Logger LOGGER = LoggerFactory.getLogger(MECInformationsTasks.class);

	@Autowired
	private CollegeRepository collegeRepository;
	@Autowired
	private CourseRepository courseRepository;
	@Autowired
	private CollegeAddressRepository collegeAddressRepository;
	@Autowired
	private MecCollegeService mecCollegeService;
	@Autowired
	private MecCourseService mecCourseService;
	@Autowired
	private MecRequester mecRequester;

//	@Scheduled(cron = "* 0 0 1 * ?")
	@Scheduled(fixedDelay = 10_000_000)
	void getAllColleges() {
		LOGGER.info("STARTING TASK GETALLCOLLEGES");

		Document document = Jsoup.parse(mecRequester.requestCollegeList());
		int lastPage = Integer.parseInt(
				document.select("select#paginationControlItemdiv_listar_consulta_avancada option").last().text());

		for (int page = 1; page <= lastPage; page++) {
			Document collegeList = Jsoup.parse(mecRequester.requestCollegeList(page));
			collegeList.select("tbody#tbyDados > tr").forEach(tr -> {
				tr.children().last().remove();

				String functionValue = tr.attr("onclick");
				String mecIdParameter = ((String) functionValue.subSequence(functionValue.indexOf("(") + 1,
						functionValue.indexOf(")"))).trim();
				long mecId = parseLong(mecIdParameter);

				Document collegeDetailsDocument = Jsoup.parse(mecRequester.requestCollegeDetails(mecIdParameter));
				College college = mecCollegeService.setupBasicCollegeInformations(collegeDetailsDocument);
				college.setMecId(mecId);

				mecCollegeService.setupCollegeGrades(college, collegeDetailsDocument);

				collegeRepository.save(college);
				LOGGER.info("SAVED COLLEGE: {}", college.getName());
			});
		}

		LOGGER.info("FINISHED TASK GETALLCOLLEGES");
	}

	// @Scheduled(cron = "* 0 0 1 * ?")
	@Scheduled(fixedDelay = 10_000_000)
	void getAllCourses() {
		LOGGER.info("STARTING TASK GETALLCOURSES");

		Document courseListDocument = Jsoup.parse(mecRequester.requestCoursesList());
		int lastPage = parseInt(courseListDocument
				.select("select#paginationControlItemdiv_listar_consulta_avancada option").last().text());
		
		int page = 2;
		do {
			LOGGER.info("REQUESTING COURSE PAGE {}\n\n", page);

			courseListDocument.select("table#tbDataGridNova #tbyDados tr").forEach(tr -> {
				Elements tds = tr.select("td");
				LOGGER.info("ANALYZING COLLEGE: {} AND COURSE: {}", tds.get(0).text(), tds.get(1).text());

				String fn = tds.get(0).text();
				String collegeMecIdParameter = ((String) fn.subSequence(fn.indexOf("(") + 1, fn.indexOf(")"))).trim();
				Long collegeMecId = parseLong(collegeMecIdParameter.split(",")[0].trim());

				Optional<College> optionalCollege = collegeRepository.findByMecId(collegeMecId);
				if (!optionalCollege.isPresent()) {
					LOGGER.error("COLLEGE NOT FOUND WITH MEC ID: {}", collegeMecId.toString());
				} else {
					College college = optionalCollege.get();
					Course course = mecCourseService.setupCourseInfo(tds);

					Optional<Course> optionalCourse = courseRepository.findByNameAndDegreeAndModality(course.getName(),
							course.getDegree(), course.getModality());

					if (!optionalCourse.isPresent()) {
						courseRepository.save(course);
						LOGGER.info("SAVED COURSE: {}", course.getName());
					} else {
						course = optionalCourse.get();
						LOGGER.info("COURSE ALREADY SAVED: {}", course.getName());
					}

					List<CourseGrade> courseGrades = mecCourseService.setupCourseGrades(tds, course);

					String courseName = tds.get(1).text().trim();
					String courseMecId = courseName.split("\\)")[0].trim().substring(1);

					Document courseDetailsListDocument = Jsoup.parse(mecRequester.requesCourseAdresses(courseMecId));
					courseDetailsListDocument.select("table#listar-ies-cadastro tbody tr").forEach(trAddress -> {
						CollegeAddress collegeAddress = mecCourseService.setupCourseAdresses(trAddress, college, courseGrades);
						collegeAddressRepository.save(collegeAddress);
					});
				}
			});

			courseListDocument = Jsoup.parse(mecRequester.requestCoursesList(page));
			page++;
		} while (page <= lastPage);

		LOGGER.info("FINISHED TASK GETALLCOURSES");
	}

}