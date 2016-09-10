package com.qualfacul.hades.course;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.qualfacul.hades.college.CollegeAddress;
import com.qualfacul.hades.user.User;

@Entity
@Table(name = "course_grade")
public class CourseGrade {

	@Id
	@GeneratedValue
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name = "grade_origin", nullable = false, length = 15)
	private CourseGradeOrigin gradeOrigin;

	@Column(name = "value")
	private Double value;

	@OneToOne(optional = true)
	private User user;

	@ManyToOne
	private Course course;

	@ManyToOne
	@JoinColumn(name = "college_address_id", unique = false)
	private CollegeAddress collegeAddress;

	// Hibernate eyes only
	@Deprecated
	public CourseGrade() {
	}

	public CourseGrade(CourseGradeOrigin gradeOrigin, Double value, Course course, CollegeAddress collegeAddress) {
		this.gradeOrigin = gradeOrigin;
		this.value = value;
		this.course = course;
		this.collegeAddress = collegeAddress;
	}

	public Long getId() {
		return id;
	}

	public CourseGradeOrigin getGradeOrigin() {
		return gradeOrigin;
	}

	public Double getValue() {
		return value;
	}

	public User getUser() {
		return user;
	}

	public Course getCourse() {
		return course;
	}

	public CollegeAddress getCollegeAddress() {
		return collegeAddress;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setGradeOrigin(CourseGradeOrigin gradeOrigin) {
		this.gradeOrigin = gradeOrigin;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public void setCollegeAddress(CollegeAddress collegeAddress) {
		this.collegeAddress = collegeAddress;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CourseGrade))
			return false;

		CourseGrade other = (CourseGrade) obj;
		return Objects.equals(this.gradeOrigin, other.gradeOrigin) &&
		Objects.equals(this.value, other.value) &&
		Objects.equals(this.collegeAddress, other.collegeAddress) &&
		Objects.equals(this.course, other.course);
	}
}