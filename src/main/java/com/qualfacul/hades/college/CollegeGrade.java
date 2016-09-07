package com.qualfacul.hades.college;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.qualfacul.hades.user.User;

@Entity
@Table(name = "college_grade")
public class CollegeGrade {

	@Id
	@GeneratedValue
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "grade_origin", nullable = false, length = 20)
	private CollegeGradeOrigin gradeOrigin;

	@NotNull
	@JsonBackReference
	@OneToOne(cascade = CascadeType.ALL)
	private College college;

	@Column(name = "date")
	private Calendar date;

	@Column(name = "value")
	private Double value;

	@OneToOne(optional = true)
	private User user;
	
	@Deprecated //Hibernate eyes only
	public CollegeGrade() {
	}
	
	public Long getId() {
		return id;
	}

	public CollegeGradeOrigin getGradeOrigin() {
		return gradeOrigin;
	}

	public College getCollege() {
		return college;
	}

	public Calendar getDate() {
		return date;
	}

	public Double getValue() {
		return value;
	}

	public User getUser() {
		return user;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setGradeOrigin(CollegeGradeOrigin gradeOrigin) {
		this.gradeOrigin = gradeOrigin;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public void setUser(User user) {
		this.user = user;
	}

}