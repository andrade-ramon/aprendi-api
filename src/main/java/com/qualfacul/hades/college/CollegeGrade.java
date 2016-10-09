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
	
	public Builder builder() {
		return new Builder();
	}
	
	public class Builder {
		
		private CollegeGrade collegeGrade = new CollegeGrade(); 
		
		public WithOrigin from(User student) {
			collegeGrade.user = student;
			return new WithOrigin();
		}
		
		public final class WithOrigin {
			public WithValue withOrigin(CollegeGradeOrigin origin) {
				collegeGrade.gradeOrigin = origin;
				return new WithValue();
			}
		}
		public final class WithValue {
			public ToCollege withValue(Double value) {
				collegeGrade.value = value;
				return new ToCollege();
			}
		}
		public final class ToCollege {
			public ToCollege to(College college) {
				collegeGrade.college = college;
				return this;
			}
			
			public CollegeGrade build() {
				collegeGrade.setDate(Calendar.getInstance());
				return collegeGrade;
			}
		}
	}
	
}