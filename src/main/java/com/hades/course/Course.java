package com.hades.course;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.hades.college.College;

@Entity
@Table(name = "course")
public class Course {

	@Id
	@GeneratedValue
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "modality")
	private String modality;
	
	@Column(name = "type")
	private String type;
	
	@ManyToMany(mappedBy = "courses")
	private List<College> colleges = new ArrayList<>();

	@Deprecated
	public Course() { //Hibernate eyes only
	}
	
	public Course(String name, String modality, String type, List<College> colleges) {
		this.name = name;
		this.modality = modality;
		this.type = type;
		this.colleges = colleges;
	}

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

	public String getModality() {
		return modality;
	}

	public void setModality(String modality) {
		this.modality = modality;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<College> getColleges() {
		return colleges;
	}

	public void setColleges(List<College> colleges) {
		this.colleges = colleges;
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Course))
			return false;
		
		Course other = (Course) obj;
		return Objects.equals(this.name, other.name);
	}
	
	
}
