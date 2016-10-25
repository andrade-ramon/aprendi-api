package com.qualfacul.hades.college;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qualfacul.hades.serialization.LocalDateTimeSerializer;

public class StudentCollegeGradeDTO {

	private Double value;
	private Long collegeId;
	private String studentName;
	private CollegeGradeOrigin origin;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime date;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Long collegeId) {
		this.collegeId = collegeId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	
	public CollegeGradeOrigin getOrigin() {
		return origin;
	}

	public void setOrigin(CollegeGradeOrigin origin) {
		this.origin = origin;
	}
	
	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}


}
