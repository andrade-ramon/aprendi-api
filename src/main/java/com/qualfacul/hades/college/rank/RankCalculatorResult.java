package com.qualfacul.hades.college.rank;

public class RankCalculatorResult {

	private Double grade;
	private Integer numberOfGrades;

	public RankCalculatorResult(Double grade, Integer numberOfGrades) {
		this.grade = grade;
		this.numberOfGrades = numberOfGrades;
	}
	
	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public Integer getNumberOfGrades() {
		return numberOfGrades;
	}

	public void setNumberOfGrades(Integer numberOfGrades) {
		this.numberOfGrades = numberOfGrades;
	}

}
