package com.qualfacul.hades.college.rank;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.qualfacul.hades.annotation.TaskService;
import com.qualfacul.hades.college.College;
import com.qualfacul.hades.college.CollegeGrade;

@TaskService
public class CollegeRankService {
	
	@Autowired
	private CollegeRankRepository collegeRankRepository;
	
	public void saveCollegeRankingFor(List<CollegeGrade> grades, CollegeRankType rankType, College college) {
		RankCalculatorResult calculatorResult = rankType.getRankingCalcultor().calculate(grades);
		
		CollegeRank generalRanking = collegeRankRepository.findByCollegeAndRankType(college, rankType)
				.map(collegeRank -> {
					collegeRank.setGrade(calculatorResult.getFinalGrade());
					collegeRank.setGradesQuantity(calculatorResult.getTotalGrades());
					return collegeRank;
				})
				.orElse(new CollegeRank(college, calculatorResult.getFinalGrade(),rankType, calculatorResult.getTotalGrades()));
		
		collegeRankRepository.save(generalRanking);
	}
}
