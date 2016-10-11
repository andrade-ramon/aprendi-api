package com.qualfacul.hades.college;

import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.EAGER;
import static org.apache.commons.lang3.StringUtils.stripAccents;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Field;

import com.qualfacul.hades.course.Course;
import com.qualfacul.hades.user.address.UserCollegeAddress;

@Entity
@Table(name = "college_address")
public class CollegeAddress {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	private College college;

	@Field
	@Column(name = "name", length = 60, nullable = true)
	private String name;

	@Column(name = "address", length = 150, nullable = false)
	private String address;

	@Column(name = "cep", length = 8, nullable = false)
	private String cep;

	@Column(name = "number", length = 20, nullable = true)
	private String number;

	@Column(name = "neighborhood", length = 55, nullable = true)
	private String neighborhood;

	@Column(name = "city", length = 33, nullable = false)
	private String city;

	@Column(name = "state", length = 2, nullable = false)
	private String state;

	@ManyToMany(fetch = FetchType.EAGER)	
	@JoinTable(name = "college_address_course", 
		joinColumns = { @JoinColumn(name = "college_address_id") }, 
		inverseJoinColumns = { @JoinColumn(name = "course_id") })
	private List<Course> courses;
	
	@OneToMany(fetch = EAGER, mappedBy = "id.collegeAddress", cascade = MERGE)
	private List<UserCollegeAddress> userCollegeAddress;
	
	@Deprecated // Hibernate eyes only
	public CollegeAddress(){}
	
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

	public List<Course> getCourses() {
		return courses;
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

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}
	
	public List<UserCollegeAddress> getUserCollegeAddress() {
		return userCollegeAddress;
	}
	
	public void setUserCollegeAddress(List<UserCollegeAddress> userCollegeAddress) {
		this.userCollegeAddress = userCollegeAddress;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CollegeAddress))
			return false;

		CollegeAddress other = (CollegeAddress) obj;
		return Objects.equals(stripAccents(this.address), stripAccents(other.address)) &&
				Objects.equals(this.cep, other.cep);
	}
}
