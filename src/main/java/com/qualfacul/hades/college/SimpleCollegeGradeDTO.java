package com.qualfacul.hades.college;

public class SimpleCollegeGradeDTO {

	private Double value;
	private CollegeGradeOrigin origin;
	
	public Double getValue() {
		return value;
	}
	
	public void setValue(Double value) {
		this.value = value;
	}
	
	public CollegeGradeOrigin getOrigin() {
		return origin;
	}
	
	public void setOrigin(CollegeGradeOrigin origin) {
		this.origin = origin;
	}
}
