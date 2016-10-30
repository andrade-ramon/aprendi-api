package com.qualfacul.hades.course;

import java.util.Objects;

public class CourseCollegesDTO {
	private Long id;
	private String name;
	private String initials;
	private String address;
	private String state;
	private String city;

	public Long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getInitials() {
		return initials;
	}

	public String getAddress() {
		return address;
	}

	public String getState() {
		return state;
	}

	public String getCity() {
		return city;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CourseCollegesDTO))
			return false;

		CourseCollegesDTO other = (CourseCollegesDTO) obj;
		return this.id == other.id && Objects.equals(this.initials, other.initials)
				&& Objects.equals(this.address, other.address);
	}
}
