package com.qualfacul.hades.college.rank;

import static com.qualfacul.hades.college.CollegeGradeOrigin.STUDENT_INFRA;

import java.util.List;
import java.util.stream.Collectors;

import com.qualfacul.hades.college.CollegeGrade;
import com.qualfacul.hades.college.CollegeGradeCalculator;

public class InfraCollegeRankCalculator implements CollegeRankCalculator {

	@Override
	public RankCalculatorResult calculate(List<CollegeGrade> gradesToFilter) {
		List<CollegeGrade> grades = gradesToFilter.stream()
			.filter(grade -> grade.getGradeOrigin().equals(STUDENT_INFRA))
			.collect(Collectors.toList());
		
		return new CollegeGradeCalculator().calculate(grades); 
	}

}
