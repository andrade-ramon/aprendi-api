package com.qualfacul.hades.college;

import java.util.ArrayList;
import java.util.List;

import com.qualfacul.hades.college.rank.RankCalculatorResult;

public class CollegeGradeCalculator {
	private final int MAX_NEGATIVE_GRADE = 4;
	
	public RankCalculatorResult calculate(List<CollegeGrade> grades) {
		List<Double> negativeGrades = new ArrayList<>();
		List<Double> positiveGrades = new ArrayList<>();

		grades.forEach(grade -> {
			if (grade.getValue() > MAX_NEGATIVE_GRADE) {
				positiveGrades.add(grade.getValue());
			} else {
				negativeGrades.add(grade.getValue());
			}
		});

		double totalNumberOfGrades = negativeGrades.size() + positiveGrades.size();

		double negativeGrade = calculateNegativeGrade(negativeGrades, totalNumberOfGrades);
		double positiveGrade = calculatePositiveGrade(positiveGrades, totalNumberOfGrades);

		double grade = positiveGrade + negativeGrade;

		return new RankCalculatorResult(grade, grades.size());
	}

	private double calculateNegativeGrade(List<Double> negativeGrades, double numberOfGrades) {
		double sumOfNegativeGrades = 0;
		for (Double grade : negativeGrades) {
			sumOfNegativeGrades += (10 - grade);
		}

		double negativePercent = 0;
		if (!negativeGrades.isEmpty()) {
			negativePercent = negativeGrades.size() / numberOfGrades;
		}

		double negativeGrade = 0;
		if (!negativeGrades.isEmpty()) {
			negativeGrade = (sumOfNegativeGrades / negativeGrades.size()) * -1;
		}

		return negativeGrade * negativePercent;
	}

	private double calculatePositiveGrade(List<Double> positiveGrades, double numberOfGrades) {
		double sumOfPositiveGrades = 0;
		for (Double grade : positiveGrades) {
			sumOfPositiveGrades += grade;
		}

		double percent = 0;
		if (!positiveGrades.isEmpty()) {
			percent = positiveGrades.size() / numberOfGrades;
		}

		double grade = 0;
		if (!positiveGrades.isEmpty()) {
			grade = (sumOfPositiveGrades / positiveGrades.size());
		}

		return grade * percent;
	}	
}
