package com.qualfacul.hades.task;

import java.util.Optional;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.qualfacul.hades.annotation.TaskComponent;
import com.qualfacul.hades.college.College;
import com.qualfacul.hades.college.CollegeRepository;
import com.qualfacul.hades.mec.MecCollegeService;
import com.qualfacul.hades.mec.MecRequester;

@TaskComponent
class MECInformationsTasks {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(MECInformationsTasks.class);
	
	@Autowired
	private CollegeRepository collegeRepository;
	@Autowired
	private MecCollegeService mecService;
	@Autowired
	private MecRequester mec;

//	@Scheduled(cron = "* 0 0 1 * ?")
	@Scheduled(fixedDelay = 10000)
	public void getAllColleges() {
		LOGGER.info("Starting task getAllCollges");

		String html = mec.requestBase();

		Document document = Jsoup.parse(html);

		int lastPage = Integer.parseInt(document.select("select#paginationControlItemdiv_listar_consulta_avancada option").last().text());

		LOGGER.info("Removing all from CollegeMec");

		for (int page = 1; page <= lastPage; page++) {
			Document baseDocument = Jsoup.parse(mec.requestBase(page));

			baseDocument.select("tbody#tbyDados > tr").forEach((trCollege) -> {
				trCollege.children().last().remove();

				College college = new College.Builder().build();

				String functionValue = trCollege.attr("onclick");
				String mecIdParameter = (String) functionValue.subSequence(functionValue.indexOf("(") + 1, functionValue.indexOf(")"));

				String response = mec.requestDetails(mecIdParameter.trim());

				Document detailsDocument = Jsoup.parse(response);

				mecService.setupBasicInformations(college, detailsDocument);
				
				mecService.setupGrades(college, detailsDocument);

				Optional<College> optionalCollege = collegeRepository.findByCnpj(college.getCnpj());
				
				if (optionalCollege.isPresent()) {
					college.setId(optionalCollege.get().getId());
				}
				collegeRepository.save(college);
				
				LOGGER.info("Saved college: {}", college.getName());
			});

		}
		LOGGER.info("Finished task getAllColleges");
	}
}
