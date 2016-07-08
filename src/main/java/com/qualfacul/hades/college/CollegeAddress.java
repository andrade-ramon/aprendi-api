package com.qualfacul.hades.college;

import static javax.persistence.CascadeType.ALL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Entity
@Table(name = "college_address")
public class CollegeAddress {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@OneToOne(cascade = ALL)
	private College college;

	@Column(name = "name")
	private String name;

	@Column(name = "address")
	private String address;

	@Column(name = "number")
	private String number;

	@Column(name = "neighborhood")
	private String neighborhood;

	@Column(name = "city")
	private String city;

	@Column(name = "state")
	private String state;

	@Deprecated // Hibernate eyes only
	public CollegeAddress() {
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

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}
}
