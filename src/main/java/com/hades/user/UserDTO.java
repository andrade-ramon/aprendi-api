package com.hades.user;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class UserDTO {
	@NotEmpty(message = "Preencha seu nome")
	private String name;

	@Email(message = "Email inválido")
	@NotEmpty(message = "Email inválido")
	private String email;

	@NotEmpty(message = "Preencha o campo senha")
	@Length(min = 4, message = "Digite uma senha com 4 caracteres ou mais")
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

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
