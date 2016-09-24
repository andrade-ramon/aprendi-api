package com.qualfacul.hades.college.rank;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.qualfacul.hades.college.College;

@Entity
@Table(name = "college_rank")
public class CollegeRank {

	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	private College college;

	@Column(name = "grade")
	private Double grade;

	@Enumerated(EnumType.STRING)
	@Column(name = "rank_type")
	private CollegeRankType rankType;

	@Column(name = "grades_quantity")
	private Integer gradesQuantity;

	@Column(name = "created_at")
	private LocalDate createdAt = LocalDate.now();

	@Deprecated // Hibernate eyes
	CollegeRank() {
	}

	public CollegeRank(College college, Double grade, CollegeRankType rankType, Integer gradesQuantity) {
		this.college = college;
		this.grade = grade;
		this.rankType = rankType;
		this.gradesQuantity = gradesQuantity;
		this.createdAt = LocalDate.now();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	public Double getGrade() {
		return grade;
	}

	public void setGrade(Double grade) {
		this.grade = grade;
	}

	public CollegeRankType getRankType() {
		return rankType;
	}

	public void setRankType(CollegeRankType rankType) {
		this.rankType = rankType;
	}

	public Integer getGradesQuantity() {
		return gradesQuantity;
	}

	public void setGradesQuantity(Integer gradesQuantity) {
		this.gradesQuantity = gradesQuantity;
	}

	public LocalDate getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}

}
