package com.qualfacul.hades.passwordrecovery;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class PasswordRecoveryDTO {
	
	@NotEmpty(message = "hades.passwordrecovery.token_unreceived")
	String token;

	@NotEmpty(message = "hades.user.empty.password")
	@Length(min = 4, message = "hades.user.invalid.password")
	String password;
	
	@Deprecated
	private PasswordRecoveryDTO(){
	}
	
	private PasswordRecoveryDTO(String token, String password){
		this.token = token;
		this.password = password;
	}

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
