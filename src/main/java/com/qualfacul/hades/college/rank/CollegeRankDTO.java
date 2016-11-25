package com.qualfacul.hades.college.rank;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qualfacul.hades.serialization.LocalDateSerializer;

public class CollegeRankDTO {

	private int position;
	
	private Long collegeId;
	
	private String collegeName;
	
	private int totalGrades;
	
	private Double grade;
	
	@JsonSerialize(using = LocalDateSerializer.class)
	private LocalDate lastUpdate;

	public int getPosition() {
		return position;
	}

	public Long getCollegeId() {
		return collegeId;
	}

	public String getCollegeName() {
		return collegeName;
	}

	public int getTotalGrades() {
		return totalGrades;
	}

	public Double getGrade() {
		return grade;
	}

	public LocalDate getLastUpdate() {
		return lastUpdate;
	}

	public void setPosition(int position) {
		this.position = position;
	}

	public void setCollegeId(Long collegeId) {
		this.collegeId = collegeId;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public void setTotalGrades(int totalGrades) {
		this.totalGrades = totalGrades;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public void setLastUpdate(LocalDate lastUpdate) {
		this.lastUpdate = lastUpdate;
	}

}
