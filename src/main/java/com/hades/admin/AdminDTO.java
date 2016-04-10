package com.hades.admin;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.hades.login.LoginOrigin;

public class AdminDTO {
	@NotNull(message="Preencha o nome do administrador")
	private String name;
	@NotNull(message="Preencha o e-mail")
	@Email(message="O e-mail informado possui formato inválido")
	private String email;
	@NotEmpty(message = "Preencha o campo senha")
	@Length(min = 4, message = "A senha deve ter no mínimo 4 caracteres")
	private String password;
	@NotEmpty(message = "A origem do usuário é obrigatória")
	private LoginOrigin loginOrigin;
	public AdminDTO(){
		
	}
	public AdminDTO(String name, String email, String password, LoginOrigin loginOrigin){
		this.name = name;
		this.email = email;
		this.password = password;
		this.loginOrigin = loginOrigin;
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
	public LoginOrigin getLoginOrigin() {
		return loginOrigin;
	}
	public void setLoginOrigin(LoginOrigin loginOrigin) {
		this.loginOrigin = loginOrigin;
	}
}
