package com.qualfacul.hades.college;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CollegeDTO {

	private Long id;
	private String name;
	private String initials;
	private String phone;
	private String cnpj;
	private String site;
	private Integer coursesCount;
	private Integer studentsCount;
	private Integer ratingsCount;
	private List<String> states = new ArrayList<>();
	private Double mecGrade;
	
	@JsonInclude(NON_NULL)
	private Boolean alreadyRated;

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

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getSite() {
		return site;
	}
	
	public Double getMecGrade() {
		return mecGrade;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public Integer getCoursesCount() {
		return coursesCount;
	}
	
	public List<String> getStates() {
		return states;
	}

	public void setCoursesCount(Integer coursesCount) {
		this.coursesCount = coursesCount;
	}

	public Integer getStudentsCount() {
		return studentsCount;
	}

	public void setStudentsCount(Integer studentsCount) {
		this.studentsCount = studentsCount;
	}

	public Integer getRatingsCount() {
		return ratingsCount;
	}

	public void setRatingsCount(Integer ratingsCount) {
		this.ratingsCount = ratingsCount;
	}

	public Boolean isAlreadyRated() {
		return alreadyRated;
	}

	public void setAlreadyRated(Boolean alreadyRated) {
		this.alreadyRated = alreadyRated;
	}
	
	public void setStates(List<String> states) {
		this.states = states;
	}
	
	public void setMecGrade(Double mecGrade) {
		this.mecGrade = mecGrade;
	}
}
