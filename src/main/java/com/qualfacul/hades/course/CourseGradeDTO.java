package com.qualfacul.hades.course;

public class CourseGradeDTO {

	private Double value;
	private CourseGradeOrigin gradeOrigin;

	public void setValue(Double value) {
		this.value = value;
	}
	
	public Double getValue() {
		return value;
	}

	public void setOrigin(CourseGradeOrigin gradeOrigin) {
		this.gradeOrigin = gradeOrigin;
	}

	public CourseGradeOrigin getOrigin() {
		return gradeOrigin;
	}
}
