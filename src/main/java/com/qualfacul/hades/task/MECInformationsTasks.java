package com.qualfacul.hades.task;

import static java.lang.Long.parseLong;

import java.util.List;
import java.util.Optional;

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
		
		String html = mecRequester.requestCollegeBasicInfo();
		Document document = Jsoup.parse(html);
		int lastPage = Integer.parseInt(
				document.select("select#paginationControlItemdiv_listar_consulta_avancada option").last().text());

		for (int page = 1; page <= lastPage; page++) {
			Document baseDocument = Jsoup.parse(mecRequester.requestCollegeBasicInfo(page));
			
			baseDocument.select("tbody#tbyDados > tr").forEach((trCollege) -> {
				trCollege.children().last().remove();

				String functionValue = trCollege.attr("onclick");
				String mecIdParameter = ((String) functionValue.subSequence(functionValue.indexOf("(") + 1,
						functionValue.indexOf(")"))).trim();
				
				String collegeDetails = mecRequester.requestCollegeDetails(mecIdParameter);
				Document collegeDetailsDocument = Jsoup.parse(collegeDetails);

				long mecId = parseLong(mecIdParameter);
				
				College college = mecCollegeService.setupBasicCollegeInformations(collegeDetailsDocument);
				college.setCollegeMec(new CollegeMec(mecId, college));
								
				String adressesDetails = mecRequester.requestCollegeAdresses(mecIdParameter);
				Document adressesDetailsDocument = Jsoup.parse(adressesDetails);
				
				mecCollegeService.setupCollegeAddress(college, adressesDetailsDocument);
				
				mecCollegeService.setupCollegeGrades(college, collegeDetailsDocument);
				collegeRepository.save(college);
				
				LOGGER.info("Saved college: {}", college.getName());
			});
		}
		
		LOGGER.info("Finished task getAllColleges");
	}
}