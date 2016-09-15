package com.qualfacul.hades.college.rank;

import java.util.List;
import java.util.Optional;

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
		
		Optional<CollegeRank> optionalCollegeRank = collegeRankRepository.findByCollegeAndRankType(college, rankType);
		
		CollegeRank generalRanking;
		if (optionalCollegeRank.isPresent()) {
			generalRanking = optionalCollegeRank.get();
			generalRanking.setGrade(calculatorResult.getGrade());
			generalRanking.setGradesQuantity(calculatorResult.getNumberOfGrades());
		} else {
			generalRanking = new CollegeRank(college, calculatorResult.getGrade(), rankType,
					calculatorResult.getNumberOfGrades());
		}
		collegeRankRepository.save(generalRanking);
	}
}
