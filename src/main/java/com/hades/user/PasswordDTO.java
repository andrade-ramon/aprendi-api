package com.hades.user;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordDTO {

	@NotEmpty(message = "hades.password.empty")
	private String password;
	@NotEmpty(message = "hades.password.empty")
	private String newPassword;

	@Deprecated
	public PasswordDTO() {
	}

	public PasswordDTO(String password, String newPassword) {
		setPassword(newPassword);
		this.newPassword = newPassword;
	}

	public String getPassword() {
		return password;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setPassword(String password) {
		this.password = BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
