package com.qualfacul.hades.college;

import static org.apache.commons.lang3.StringUtils.stripAccents;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.qualfacul.hades.course.CourseGrade;

@Entity
@Table(name = "college_address")
public class CollegeAddress {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private College college;

	@Column(name = "name")
	private String name;

	@Column(name = "address")
	private String address;

	@Column(name = "cep")
	private String cep;

	@Column(name = "number")
	private String number;

	@Column(name = "neighborhood")
	private String neighborhood;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinTable(name = "college_course", 
		joinColumns = { @JoinColumn(name = "college_address_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "course_grade_id") })
	private List<CourseGrade> courseGrades = new ArrayList<>();

	@Deprecated // Hibernate eyes only
	public CollegeAddress() {
	}
	
	public CollegeAddress(College college) {
		this.college = college;
	}
	
	public Long getId() {
		return id;
	}

	public College getCollege() {
		return college;
	}

	public String getName() {
		return name;
	}

	public String getAddress() {
		return address;
	}

	public String getCep() {
		return cep;
	}

	public String getNumber() {
		return number;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public List<CourseGrade> getCourseGrades() {
		return courseGrades;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCourseGrades(List<CourseGrade> courseGrades) {
		this.courseGrades = courseGrades;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CollegeAddress))
			return false;

		CollegeAddress other = (CollegeAddress) obj;
		
		return Objects.equals(stripAccents(this.address), stripAccents(other.address)) &&
				Objects.equals(stripAccents(this.cep), stripAccents(other.cep)) &&
				Objects.equals(stripAccents(this.city), stripAccents(other.city)) &&
				Objects.equals(stripAccents(this.state), stripAccents(other.state));
	}

}
