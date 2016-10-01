package com.qualfacul.hades.college;

public class UserCollegeAddressDTO {

	private Long collegeId;
	private Long collegeAddressId;
	private String studentRa;
	
	public Long getCollegeAddressId() {
		return collegeAddressId;
	}
	public void setCollegeAddressId(Long collegeAddressId) {
		this.collegeAddressId = collegeAddressId;
	}
	public String getStudentRa() {
		return studentRa;
	}
	public void setStudentRa(String studentRa) {
		this.studentRa = studentRa;
	}
	public Long getCollegeId() {
		return collegeId;
	}
	public void setCollegeId(Long collegeId) {
		this.collegeId = collegeId;
	}
}
