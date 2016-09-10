package com.qualfacul.hades.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.qualfacul.hades.annotation.TaskComponent;
import com.qualfacul.hades.college.CollegeAddressRepository;
import com.qualfacul.hades.college.CollegeRepository;
import com.qualfacul.hades.course.CourseGradeRepository;
import com.qualfacul.hades.course.CourseRepository;
import com.qualfacul.hades.mec.MecCourseService;
import com.qualfacul.hades.mec.MecRequester;

@TaskComponent
public class MecCourseTask {
	private static final Logger LOGGER = LoggerFactory.getLogger(MecCourseTask.class);

	@Value("${tasks.threads.number}")
	private int NUMBER_OF_THREADS;
	private CollegeRepository collegeRepository;
	private CourseRepository courseRepository;
	private CourseGradeRepository courseGradeRepository;
	private CollegeAddressRepository collegeAddressRepository;
	private MecCourseService mecCourseService;
	private MecRequester mecRequester;

	@Autowired
	MecCourseTask(CollegeRepository collegeRepository, CourseRepository courseRepository,
			CourseGradeRepository courseGradeRepository, CollegeAddressRepository collegeAddressRepository,
			MecCourseService mecCourseService, MecRequester mecRequester) {

		this.collegeRepository = collegeRepository;
		this.courseRepository = courseRepository;
		this.courseGradeRepository = courseGradeRepository;
		this.collegeAddressRepository = collegeAddressRepository;
		this.mecCourseService = mecCourseService;
		this.mecRequester = mecRequester;

	}

	public void getAllCourses() {
		LOGGER.info("STARTED TASK GETALLCOURSES");

		Document courseListDocument = Jsoup.parse(mecRequester.requestCoursesList());
		int totalPages = Integer.parseInt(courseListDocument
				.select("select#paginationControlItemdiv_listar_consulta_avancada option").last().text());

		ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
		int page = 1;
		do {
			Runnable courseWorker = new CourseWorker(mecRequester, collegeRepository, mecCourseService,
					courseRepository, collegeAddressRepository, courseGradeRepository, page++);
			executor.execute(courseWorker);
		} while (page <= totalPages);

		executor.shutdown();
		while (!executor.isTerminated()) {
		}

		LOGGER.info("FINISHED TASK GETALLCOURSES");
	}
}