package com.qualfacul.hades.college;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import com.qualfacul.hades.user.User;

@Indexed
@Entity
@Table(name = "college")
public class College {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@Column(name = "mec_id", unique = true)
	private long mecId;

	@Field
	@Column(name = "name", nullable = false)
	@Boost(2.5f)
	private String name;

	@Field
	@Column(name = "initials", length = 30)
	@Boost(3.0f)
	private String initials;

	@Field
	@Column(name = "phone")
	@Boost(0.5f)
	private String phone;

	@Field
	@Column(name = "cnpj")
	private String cnpj;

	@Field
	@Column(name = "site")
	@Boost(0.7f)
	private String site;

	@IndexedEmbedded
	@OneToMany(cascade = CascadeType.MERGE, mappedBy = "college")
	private List<CollegeAddress> addresses = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "college", fetch = FetchType.EAGER)
	private List<CollegeGrade> grades = new ArrayList<>();
	
	public Long getId() {
		return id;
	}

	public long getMecId() {
		return mecId;
	}

	public String getName() {
		return name;
	}

	public String getInitials() {
		return initials;
	}

	public String getPhone() {
		return phone;
	}

	public String getCnpj() {
		return cnpj;
	}

	public String getSite() {
		return site;
	}

	public List<CollegeAddress> getAddresses() {
		return addresses;
	}

	public List<CollegeGrade> getGrades() {
		return grades;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMecId(long mecId) {
		this.mecId = mecId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setInitials(String initials) {
		this.initials = initials;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public void setSite(String site) {
		this.site = site;
	}

	public void setAdresses(List<CollegeAddress> adresses) {
		this.addresses = adresses;
	}

	public void setGrades(List<CollegeGrade> grades) {
		this.grades = grades;
	}
	
	public void rate(User student, CollegeGradeOrigin origin, Double gradeValue) {
		CollegeGrade collegeGrade = new CollegeGrade().builder()
						.from(student)
						.withOrigin(origin)
						.withValue(gradeValue)
						.to(this)
						.build();
		grades.add(collegeGrade);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof College))
			return false;

		College other = (College) obj;
		return Objects.equals(this.id, other.id);
	}

}
