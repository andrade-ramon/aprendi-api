package com.qualfacul.hades.college;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class CollegeLoginDTO {
	
	@NotEmpty(message = "hades.user.empty.password")
	@Length(min = 6, message = "hades.user.invalid.password")
	private String password;
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
