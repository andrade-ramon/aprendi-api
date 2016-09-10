package com.qualfacul.hades.task;

import static java.lang.Long.parseLong;

import java.util.List;
import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qualfacul.hades.college.College;
import com.qualfacul.hades.college.CollegeAddress;
import com.qualfacul.hades.college.CollegeAddressRepository;
import com.qualfacul.hades.college.CollegeRepository;
import com.qualfacul.hades.course.Course;
import com.qualfacul.hades.course.CourseGrade;
import com.qualfacul.hades.course.CourseGradeRepository;
import com.qualfacul.hades.course.CourseModality;
import com.qualfacul.hades.course.CourseRepository;
import com.qualfacul.hades.mec.MecCourseService;
import com.qualfacul.hades.mec.MecRequester;

class CourseWorker implements Runnable {
	private static final Logger LOGGER = LoggerFactory.getLogger(CourseWorker.class);
	private MecRequester mecRequester;
	private CollegeRepository collegeRepository;
	private MecCourseService mecCourseService;
	private CourseRepository courseRepository;
	private int page;
	private CourseGradeRepository courseGradeRepository;

	CourseWorker(MecRequester mecRequester, CollegeRepository collegeRepository, MecCourseService mecCourseService,
			CourseRepository courseRepository, CollegeAddressRepository collegeAddressRepository,
			CourseGradeRepository courseGradeRepository, int page) {
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
				if (course.getModality().equals(CourseModality.PRESENTIAL)) {
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
