package com.qualfacul.hades.course;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
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
	
	@ManyToMany(mappedBy = "courseGrades", fetch = FetchType.EAGER)
	private List<CollegeAddress> collegeAdresses = new ArrayList<>();

	@OneToOne(fetch = FetchType.EAGER)
	private Course course;

	@Deprecated
	public CourseGrade() { // Hibernate eyes only
	}

	public CourseGrade(CourseGradeOrigin gradeOrigin, Double value, Course course) {
		this.gradeOrigin = gradeOrigin;
		this.value = value;
		this.course = course;
	}

	public CourseGrade(CourseGradeOrigin gradeOrigin, Double value, List<CollegeAddress> collegeAdresses) {
		this.gradeOrigin = gradeOrigin;
		this.value = value;
		this.collegeAdresses = collegeAdresses;
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

	public List<CollegeAddress> getCollegeAdresses() {
		return collegeAdresses;
	}

	public Course getCourse() {
		return course;
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

	public void setCollegeAdresses(List<CollegeAddress> collegeAdresses) {
		this.collegeAdresses = collegeAdresses;
	}

	public void setCourse(Course course) {
		this.course = course;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof CourseGrade))
			return false;

		CourseGrade other = (CourseGrade) obj;
		return Objects.equals(this.id, other.id);
	}
}