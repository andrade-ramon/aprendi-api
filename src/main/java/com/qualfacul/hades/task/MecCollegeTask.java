package com.qualfacul.hades.task;

import static java.lang.Long.parseLong;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.qualfacul.hades.annotation.TaskComponent;
import com.qualfacul.hades.college.CollegeRepository;
import com.qualfacul.hades.mec.MecCollegeService;
import com.qualfacul.hades.mec.MecRequester;

@TaskComponent
public class MecCollegeTask {
	private static final Logger LOGGER = LoggerFactory.getLogger(MecCourseTask.class);

	@Value("${tasks.threads.number}")
	private int NUMBER_OF_THREADS;
	@Autowired
	private CollegeRepository collegeRepository;
	@Autowired
	private MecCollegeService mecCollegeService;
	@Autowired
	private MecRequester mecRequester;

	public void getAllColleges() {
		LOGGER.info("STARTED TASK GETALLCOLLEGES");

		ExecutorService executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
		
		Document collegeList = Jsoup.parse(mecRequester.requestCollegeList(1));
		int totalPages = Integer.parseInt(
				collegeList.select("select#paginationControlItemdiv_listar_consulta_avancada option").last().text());
		int page = 1;
		do {
			collegeList.select("tbody#tbyDados > tr").forEach(tr -> {
				tr.children().last().remove();
				String id = tr.attr("onclick");
				Long mecId = parseLong(id.subSequence(id.indexOf("(") + 1, id.indexOf(")")).toString().trim());

				Runnable collegeWorker = new CollegeWorker(mecId, mecRequester, mecCollegeService,
						collegeRepository);
				executor.execute(collegeWorker);
			});
			
			LOGGER.info("REQUESTING PAGE " + page + " OF " + totalPages);
			collegeList = Jsoup.parse(mecRequester.requestCollegeList(++page));
		} while (page <= totalPages);

		executor.shutdown();
		while (!executor.isTerminated()) {
		}
		LOGGER.info("FINISHED TASK GETALLCOLLEGES");
	}
}
