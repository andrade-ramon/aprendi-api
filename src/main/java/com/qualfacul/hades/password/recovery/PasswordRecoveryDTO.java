package com.qualfacul.hades.password.recovery;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class PasswordRecoveryDTO {
	
	@NotEmpty(message = "hades.passwordrecovery.token.not.received")
	private String token;

	@NotEmpty(message = "hades.user.empty.password")
	@Length(min = 4, message = "hades.user.invalid.password")
	private String password;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
