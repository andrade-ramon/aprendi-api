package com.hades.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class UserDTO {

	@NotEmpty(message = "hades.user.empty.name")
	private String name;

	@Email(message = "hades.user.invalid.email")
	@NotEmpty(message = "hades.user.invalid.email")
	private String email;

	@NotEmpty(message = "hades.user.empty.password")
	@Length(min = 4, message = "hades.user.invalid.password")
	private String password;

	public UserDTO() {
	}

	public UserDTO(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}