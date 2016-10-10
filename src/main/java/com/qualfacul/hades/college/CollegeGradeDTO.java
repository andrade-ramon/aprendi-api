package com.qualfacul.hades.college;

import java.util.Calendar;

public class CollegeGradeDTO {

	private Double value;
	private CollegeGradeOrigin origin;
	private Long collegeId;
	private Long userId;
	private Calendar date;

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public CollegeGradeOrigin getOrigin() {
		return origin;
	}

	public void setOrigin(CollegeGradeOrigin gradeOrigin) {
		this.origin = gradeOrigin;
	}

	public Long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Long collegeId) {
		this.collegeId = collegeId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

}
