package com.qualfacul.hades.college;

import static javax.persistence.CascadeType.ALL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "college_mec")
public class CollegeMec {
	@Id
	@Column(name = "mec_id")
	private Long mecId;

	@OneToOne(cascade = ALL, optional = false)
	private College college;

	@Deprecated // Hibernate eyes only
	CollegeMec() {
	}
	
	public CollegeMec(Long mecId, College college) {
		this.mecId = mecId;
		this.college = college;
	}

	public CollegeMec(Long mecId) {
		this.mecId = mecId;
	}

	public Long getId() {
		return mecId;
	}

	public College getCollege() {
		return college;
	}

	public void setId(Long id) {
		this.mecId = id;
	}

	public void setCollege(College college) {
		this.college = college;
	}

}