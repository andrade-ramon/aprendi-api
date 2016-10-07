package com.qualfacul.hades.user.address;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.qualfacul.hades.college.CollegeAddress;
import com.qualfacul.hades.user.User;

@Embeddable
public class UserCollegeAddressId implements Serializable {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private User user;
	@ManyToOne
	private CollegeAddress collegeAddress;

	public UserCollegeAddressId(CollegeAddress collegeAddress, User student) {
		this.collegeAddress = collegeAddress;
		this.user = student;
	}
	
	@Deprecated
	public UserCollegeAddressId() {} //Hibernate eyes only

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public CollegeAddress getCollegeAddress() {
		return collegeAddress;
	}

	public void setCollegeAddress(CollegeAddress collegeAddress) {
		this.collegeAddress = collegeAddress;
	}

}
