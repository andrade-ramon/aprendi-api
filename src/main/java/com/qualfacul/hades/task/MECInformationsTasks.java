package com.qualfacul.hades.task;

import static java.lang.Long.parseLong;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.qualfacul.hades.college.College;
import com.qualfacul.hades.college.CollegeMec;
import com.qualfacul.hades.college.CollegeRepository;
import com.qualfacul.hades.mec.MecCollegeService;
import com.qualfacul.hades.mec.MecRequester;


@Component
class MECInformationsTasks {

	private static final Logger LOGGER = LoggerFactory.getLogger(MECInformationsTasks.class);

	@Autowired
	private CollegeRepository collegeRepository;
	@Autowired
	private MecCollegeService mecCollegeService;
	@Autowired
	private MecRequester mecRequester;	

//	@Scheduled(cron = "* 0 0 1 * ?")
	@Scheduled(fixedDelay = 10_000_000)
	void getAllColleges() {
		LOGGER.info("Starting task getAllCollges");
		
		String html = mecRequester.requestCollegeBasic();
		Document document = Jsoup.parse(html);
		int lastPage = Integer.parseInt(
				document.select("select#paginationControlItemdiv_listar_consulta_avancada option").last().text());

		for (int page = 1; page <= lastPage; page++) {
			Document baseDocument = Jsoup.parse(mecRequester.requestCollegeBasic(page));
			
			baseDocument.select("tbody#tbyDados > tr").forEach((trCollege) -> {
				trCollege.children().last().remove();

				String functionValue = trCollege.attr("onclick");
				String mecIdParameter = (String) functionValue.subSequence(functionValue.indexOf("(") + 1,
						functionValue.indexOf(")"));
				
				String response = mecRequester.requestCollegeDetails(mecIdParameter.trim());

				Document detailsDocument = Jsoup.parse(response);

				long mecId = parseLong(mecIdParameter.trim());
				
				College college = mecCollegeService.setupBasicCollegeInformations(detailsDocument);
				CollegeMec collegeMec = new CollegeMec(mecId, college);
				
				college.setCollegeMec(collegeMec);

				mecCollegeService.setupCollegeGrades(college, detailsDocument);
				collegeRepository.save(college);
				
				
				LOGGER.info("Saved college: {}", college.getName());
			});
		}
		LOGGER.info("Finished task getAllColleges");
	}

//	// @Scheduled(cron = "* 0 0 1 * ?")
//	@Scheduled(fixedDelay = 10_000_000)
//	void getAllCoursesFromColleges() {
//		LOGGER.info("Starting task getAllCoursesFromColleges");
//
//		Long count = collegeRepository.count();
//		for (long collegeId = 1; collegeId <= count; collegeId++) {
//			Optional<CollegeMecDTO> optionalCollege = collegeRepository.findById(collegeId);
//
//			optionalCollege.ifPresent(college -> {
//				Long mecId = college.getMecId();
//				try {
//					String responseForPage = mec.requestCollegeCourses(mecId);
//					Document coursesDocForPage = Jsoup.parse(responseForPage);
//
//					int lastPage = Integer.parseInt(coursesDocForPage
//							.select("select#paginationControlItemdivListarCurso option").last().text());
//
//					for (int page = 1; page <= lastPage; page++) {
//						String detailsResponse = mec.requestCollegeCourses(mecId, page);
//						Document coursesDoc = Jsoup.parse(detailsResponse);
//
//						coursesDoc.select("table#listar-ies-cadastro tr td:first-child > a").forEach(a -> {
//							String courseResponse = mec.requestCourse(a.attr("href"));
//							Document courseDoc = Jsoup.parse(courseResponse);
//
//							mecCourseService.setupCoursesInformations(courseDoc, college).ifPresent(courseDTO -> {
//								collegeRepository.saveGradeFor(college.getId(), courseDTO);
//							});
//						});
//					}
//				} catch (NullPointerException e) {
//					LOGGER.error("College {} not found on mec", college.getName());
//				}
//			});
//
//		}
//
//	}
}