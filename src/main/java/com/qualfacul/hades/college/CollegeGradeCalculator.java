package com.qualfacul.hades.college;

import java.util.ArrayList;
import java.util.List;

import com.qualfacul.hades.college.rank.RankCalculatorResult;

public class CollegeGradeCalculator {

	private final int MAX_NEGATIVE_GRADE = 4;

	public RankCalculatorResult calculate(List<CollegeGrade> grades) {
		List<Double> negativeGrades = new ArrayList<>();
		List<Double> positiveGrades = new ArrayList<>();
		
		grades.stream()
			  .filter(grade -> grade.getValue() > MAX_NEGATIVE_GRADE)
			  .map(grade -> positiveGrades.add(grade.getValue()));
		
		grades.stream()
		  .filter(grade -> grade.getValue() < MAX_NEGATIVE_GRADE)
		  .map(grade -> positiveGrades.add(grade.getValue()));
		
		return new RankCalculatorResult().builder()
			.withSize(grades.size())
			.withNegatives(negativeGrades)
			.withPositives(positiveGrades)
			.build();
	}
}
