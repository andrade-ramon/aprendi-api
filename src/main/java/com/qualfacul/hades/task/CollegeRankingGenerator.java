package com.qualfacul.hades.task;

import static java.util.Arrays.stream;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.qualfacul.hades.annotation.TaskComponent;
import com.qualfacul.hades.college.CollegeGrade;
import com.qualfacul.hades.college.CollegeRepository;
import com.qualfacul.hades.college.rank.CollegeRankService;
import com.qualfacul.hades.college.rank.CollegeRankType;

@TaskComponent
public class CollegeRankingGenerator {
	private static final Logger LOGGER = LoggerFactory.getLogger(CollegeRankingGenerator.class);

	@Autowired
	private CollegeRepository collegeRepository;
	@Autowired
	private CollegeRankService collegeRankService;

	@Transactional
	public void generateRanking() {
		collegeRepository.findAll().forEach(college -> {
			LOGGER.info("GENERATING RANKING GRADE TO COLLEGE {}", college.getId());
			List<CollegeGrade> grades = college.getGrades();

			stream(CollegeRankType.values()).forEach(rankType -> {
				collegeRankService.saveCollegeRankingFor(grades, rankType, college);
			});
		});
	}

}
