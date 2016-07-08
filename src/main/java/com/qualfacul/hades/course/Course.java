//package com.qualfacul.hades.course;
//
//import static com.qualfacul.hades.validation.AccentNormalizer.removeAccents;
//import static javax.persistence.CascadeType.ALL;
//import static javax.persistence.EnumType.STRING;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.Enumerated;
//import javax.persistence.GeneratedValue;
//import javax.persistence.Id;
//import javax.persistence.ManyToMany;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
//import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
//
//import com.qualfacul.hades.college.College;
//import com.qualfacul.hades.validation.AccentNormalizer;
//
//@Entity
//@Table(name = "course")
//public class Course {
//
//	@Id
//	@GeneratedValue
//	private Long id;
//	
//	@Column(name = "name", nullable = false)
//	private String name;
//
//	@Enumerated(STRING)
//	@Column(name = "modality", nullable = false, length = 15)
//	private CourseModality modality;
//	
//	@Enumerated(STRING)
//	@Column(name = "degree", nullable = false, length = 15)
//	private CourseDegree degree;
//	
//	@OneToMany(mappedBy = "course", cascade = ALL)
//	private List<CourseGrade> grades = new ArrayList<>(); 
//	
//	@ManyToMany(mappedBy = "courses")
//	private List<College> colleges = new ArrayList<>();
//
//	@Deprecated
//	public Course() { //Hibernate eyes only
//	}
//	
//	public Course(String name, CourseModality modality, CourseDegree degree, List<CourseGrade> grades ,List<College> colleges) {
//		this.name = name;
//		this.modality = modality;
//		this.degree = degree;
//		this.grades = grades;
//		this.colleges = colleges;
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
//	public String getName() {
//		return name;
//	}
//
//	public void setName(String name) {
//		this.name = name;
//	}
//
//	public CourseModality getModality() {
//		return modality;
//	}
//
//	public void setModality(CourseModality modality) {
//		this.modality = modality;
//	}
//
//	public CourseDegree getDegree() {
//		return degree;
//	}
//
//	public void setDegree(CourseDegree degree) {
//		this.degree = degree;
//	}
//	
//	public List<CourseGrade> getGrades() {
//		return grades;
//	}
//
//	public void setGrades(List<CourseGrade> grades) {
//		this.grades = grades;
//	}
//
//	public List<College> getColleges() {
//		return colleges;
//	}
//
//	public void setColleges(List<College> colleges) {
//		this.colleges = colleges;
//	}
//	
//	public void addCollege(College college) {
//		this.colleges.add(college);
//	}
//
//	public void addGrade(CourseGrade grade) {
//		this.grades.add(grade);
//	}
//	
//	@Override
//	public String toString() {
//		return ReflectionToStringBuilder.toString(this);
//	}
//
//	@Override
//	public int hashCode() {
//		return Objects.hash(name);
//	}
//
//	@Override
//	public boolean equals(Object obj) {
//		if (this == obj)
//			return true;
//		if (!(obj instanceof Course))
//			return false;
//		
//		Course other = (Course) obj;
//		return Objects.equals(removeAccents(this.name), removeAccents(other.name));
//	}
//
//}