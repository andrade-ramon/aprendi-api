package com.hades.college;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.hades.course.Course;
import com.qualfacul.hermes.college.Address;

@Entity
@Table(name = "college")
public class College {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "initials", length = 30)
	private String initials;

	@AttributeOverride(name = "value", column = @Column(name = "address"))
	@Embedded
	private Address address;
	
	@Column(name = "phone")
	private String phone;

	@Column(name = "cnpj")
	private String cnpj;

	@Column(name = "site")
	private String site;

	@OneToMany(mappedBy = "college", cascade = ALL)
	private List<CollegeGrade> grades = new ArrayList<>();
	
	@ManyToMany(fetch = LAZY, cascade = ALL)
	@JoinTable(name = "college_course",
			   joinColumns = { @JoinColumn(name = "college_id") },
			   inverseJoinColumns = { @JoinColumn(name = "course_id") })
	private List<Course> courses = new ArrayList<>();

	@Deprecated // Hibernate eyes only
	College() {
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

	public String getInitials() {
		return initials;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getSite() {
		return site;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public List<CollegeGrade> getGrades() {
		return grades;
	}

	public void setGrades(List<CollegeGrade> grades) {
		this.grades = grades;
	}
	
	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public static class Builder {
		private College college;
		
		public Builder() {
			college = new College();
		}
		
		Builder withId(Long id) {
			this.college.setId(id);
			return this;
		}
		
		public Builder withName(String name) {
			this.college.setName(name);
			return this;
		}

		public Builder withInitials(String initials) {
			this.college.setInitials(initials);
			return this;
		}

		public Builder withAddress(Address address) {
			this.college.setAddress(address);
			return this;
		}

		public Builder withPhone(String phone) {
			this.college.setPhone(phone);
			return this;
		}

		public Builder withCnpj(String cnpj) {
			this.college.setCnpj(cnpj);
			return this;
		}

		public Builder withSite(String site) {
			this.college.setSite(site);
			return this;
		}

		public College build() {
			return this.college;
		}

	}
	
}