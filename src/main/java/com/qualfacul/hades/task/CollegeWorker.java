package com.qualfacul.hades.task;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.qualfacul.hades.college.College;
import com.qualfacul.hades.college.CollegeRepository;
import com.qualfacul.hades.mec.MecCollegeService;
import com.qualfacul.hades.mec.MecRequester;

public class CollegeWorker implements Runnable {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CollegeWorker.class);
	private Long mecId;
	private MecRequester mecRequester;
	private MecCollegeService mecCollegeService;
	private CollegeRepository collegeRepository;

	CollegeWorker(Long mecId, MecRequester mecRequester, MecCollegeService mecCollegeService,
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
