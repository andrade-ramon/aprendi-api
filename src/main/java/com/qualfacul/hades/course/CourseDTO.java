package com.qualfacul.hades.course;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CourseDTO {

	private Long id;
	
	private String name;
	
	private CourseModality modality;
	
	private CourseDegree degree;
	
	@JsonInclude(NON_NULL)
	private List<CourseGradeDTO> courseGrades;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public CourseModality getModality() {
		return modality;
	}
	public void setModality(CourseModality modality) {
		this.modality = modality;
	}
	public CourseDegree getDegree() {
		return degree;
	}
	public void setDegree(CourseDegree degree) {
		this.degree = degree;
	}
	public List<CourseGradeDTO> getCourseGrades() {
		return courseGrades;
	}
	public void setCourseGrades(List<CourseGradeDTO> courseGrades) {
		this.courseGrades = courseGrades;
	}
}
