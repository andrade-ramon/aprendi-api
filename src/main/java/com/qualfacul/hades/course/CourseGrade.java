//package com.qualfacul.hades.course;
//
//import static javax.persistence.CascadeType.ALL;
//
//import java.util.Optional;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.EnumType;
//import javax.persistence.Enumerated;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.OneToOne;
//import javax.persistence.Table;
//
//import com.qualfacul.hades.college.College;
//import com.qualfacul.hades.user.User;
//
//@Entity
//@Table(name = "course_grade")
//public class CourseGrade {
//
//	@Id
//	@GeneratedValue
//	private Long id;
//	
//	@OneToOne(cascade = ALL)
//	private Course course;
//	
//	@OneToOne(cascade = ALL)
//	private College college;
//	
//	@Enumerated(EnumType.STRING)
//	@Column(name = "grade_origin", nullable = false, length = 15)
//	private CourseGradeOrigin gradeOrigin;
//	
//	@Column(name = "value", nullable = false)
//	private Double value;
//	
//	@OneToOne(optional = true)
//	private User user;
//
//	@Deprecated
//	public CourseGrade() { //Hibernate eyes only
//	}
//
//	public CourseGrade(Long id, Course course, CourseGradeOrigin gradeOrigin, Double value, User user) {
//		this.id = id;
//		this.course = course;
//		this.gradeOrigin = gradeOrigin;
//		this.value = value;
//		this.user = user;
//	}
//
//	public Long getId() {
//		return id;
//	}
//
//	public void setId(Long id) {
//		this.id = id;
//	}
//
//	public Course getCourse() {
//		return course;
//	}
//
//	public void setCourse(Course course) {
//		this.course = course;
//	}
//
//	public CourseGradeOrigin getGradeOrigin() {
//		return gradeOrigin;
//	}
//
//	public void setGradeOrigin(CourseGradeOrigin gradeOrigin) {
//		this.gradeOrigin = gradeOrigin;
//	}
//
//	public Double getValue() {
//		return value;
//	}
//
//	public void setValue(Double value) {
//		this.value = value;
//	}
//
//	public Optional<User> getUser() {
//		return Optional.ofNullable(user);
//	}
//
//	public void setUser(User user) {
//		this.user = user;
//	}
//	
//	public College getCollege() {
//		return college;
//	}
//
//	public void setCollege(College college) {
//		this.college = college;
//	}
//	
//}