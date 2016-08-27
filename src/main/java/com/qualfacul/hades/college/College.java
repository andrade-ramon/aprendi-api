package com.qualfacul.hades.college;

import static javax.persistence.CascadeType.ALL;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

@Indexed
@Entity
@Table(name = "college")
public class College {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Column(name = "mec_id", unique = true)
	private long mecId;

	@Field
	@Column(name = "name", nullable = false)
	@Boost(2.5f)
	private String name;

	@Field
	@Column(name = "initials", length = 30)
	@Boost(3.0f)
	private String initials;

	@IndexedEmbedded
	@OneToMany(mappedBy = "college", cascade = CascadeType.MERGE)
	private List<CollegeAddress> collegeAdresses = new ArrayList<>();

	@Field
	@Column(name = "phone")
	@Boost(0.5f)
	private String phone;

	@Field
	@Column(name = "cnpj")
	private String cnpj;

	@Field
	@Column(name = "site")
	@Boost(0.7f)
	private String site;
	
	@OneToMany(mappedBy = "college", cascade = ALL)
	private List<CollegeGrade> grades = new ArrayList<>();
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public long getMecId() {
		return mecId;
	}

	public void setMecId(long mecId) {
		this.mecId = mecId;
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
