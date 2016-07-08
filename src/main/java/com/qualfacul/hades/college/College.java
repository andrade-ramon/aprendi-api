package com.qualfacul.hades.college;

import static javax.persistence.CascadeType.ALL;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "college")
public class College {

	@Id
	@GeneratedValue
	private Long id;
		
	@OneToOne(mappedBy = "college", cascade = ALL)
	private CollegeMec collegeMec;
	
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "initials", length = 30)
	private String initials;

	@OneToMany(mappedBy = "college", cascade = ALL)
	private List<CollegeAddress> collegeAdresses = new ArrayList<>();
	
	@Column(name = "phone")
	private String phone;

	@Column(name = "cnpj")
	private String cnpj;

	@Column(name = "site")
	private String site;

	@OneToMany(mappedBy = "college", cascade = ALL)
	private List<CollegeGrade> grades = new ArrayList<>();

	@Deprecated // Hibernate eyes only
	public College() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public CollegeMec getCollegeMec() {
		return collegeMec;
	}

	public void setCollegeMec(CollegeMec collegeMec) {
		this.collegeMec = collegeMec;
		collegeMec.setCollege(this);
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

	public List<CollegeAddress> getCollegeAdresses() {
		return collegeAdresses;
	}

	public void setCollegeAdresses(List<CollegeAddress> collegeAdresses) {
		this.collegeAdresses = collegeAdresses;
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

	public void setSite(String site) {
		this.site = site;
	}

	public List<CollegeGrade> getGrades() {
		return grades;
	}

	public void setGrades(List<CollegeGrade> grades) {
		this.grades = grades;
	}
	
	
}
