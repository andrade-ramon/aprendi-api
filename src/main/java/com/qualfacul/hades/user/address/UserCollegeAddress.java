package com.qualfacul.hades.user.address;

import javax.persistence.AssociationOverride;
import javax.persistence.AssociationOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "user_college_address")
@AssociationOverrides({
	@AssociationOverride(name = "id.user", joinColumns = @JoinColumn(name = "user_id")),
	@AssociationOverride(name = "id.collegeAddress", joinColumns = @JoinColumn(name = "college_address_id")),
})
public class UserCollegeAddress {

	@EmbeddedId
	private UserCollegeAddressId id;
	
	@Column(name = "student_ra", length = 20, nullable = false)
	private String studentRa;
	
	@Deprecated
	public UserCollegeAddress() {} //Hibernate eyes only

	public UserCollegeAddress(UserCollegeAddressId id, String studentRa) {
		this.id = id;
		this.studentRa = studentRa;
	}

	public UserCollegeAddressId getId() {
		return id;
	}
	
	public String getStudentRa() {
		return studentRa;
	}
	
	public void setStudentRa(String studentRa) {
		this.studentRa = studentRa;
	}
}
