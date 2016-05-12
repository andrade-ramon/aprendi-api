package com.hades.user;

import java.util.Calendar;

import org.hibernate.validator.constraints.NotEmpty;

public class UserInfoDTO {
	@NotEmpty(message = "hades.userProfile.name.empty")
	private String name;
	private Calendar bornDate;
	private Boolean acceptEmail;

	public UserInfoDTO() {
	}

	public UserInfoDTO(String name, Calendar bornDate, Boolean acceptEmail) {
		this.name = name;
		this.bornDate = bornDate;
		this.acceptEmail = acceptEmail;
	}

	public String getName() {
		return name;
	}

	public Calendar getBornDate() {
		return bornDate;
	}

	public Boolean getAcceptEmail() {
		return acceptEmail;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setBornDate(Calendar bornDate) {
		this.bornDate = bornDate;
	}

	public void setAcceptEmail(Boolean acceptEmail) {
		this.acceptEmail = acceptEmail;
	}

}
