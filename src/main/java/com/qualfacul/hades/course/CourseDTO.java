package com.qualfacul.hades.course;

public class CourseDTO {

	private Long id;
	private String name;
	private CourseModality modality;
	private CourseDegree degree;

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
}
