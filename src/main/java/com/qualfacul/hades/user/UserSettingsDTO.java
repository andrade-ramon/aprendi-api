package com.qualfacul.hades.user;

import org.hibernate.validator.constraints.Length;

public class UserSettingsDTO {
	private String name;

	@Length(min = 4, message = "settings.user.invalid.password")
	private String password;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
