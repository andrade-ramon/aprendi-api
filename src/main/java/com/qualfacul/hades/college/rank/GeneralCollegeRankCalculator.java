package com.qualfacul.hades.college.rank;

import java.util.List;
import java.util.stream.Collectors;

import com.qualfacul.hades.college.CollegeGrade;
import com.qualfacul.hades.college.CollegeGradeCalculator;

class GeneralCollegeRankCalculator implements CollegeRankCalculator {

	@Override
	public RankCalculatorResult calculate(List<CollegeGrade> gradesToFilter) {
		List<CollegeGrade> grades = gradesToFilter.stream().filter(grade -> grade.getUser() != null)
				.collect(Collectors.toList());

		return new CollegeGradeCalculator().calculate(grades);
	}
}
