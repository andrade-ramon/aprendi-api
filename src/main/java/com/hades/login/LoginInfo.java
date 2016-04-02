package com.hades.login;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "login_info")
public class LoginInfo {
	
	@Id
	@NotNull
	private String login;
	@NotNull
	private String password;

	@Transient
	private String token;

	@Deprecated // Hibernate eyes only
	public LoginInfo() {
	}

	public LoginInfo(String login, String password) {
		this.login = login;
		this.password = password;		
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public String getToken() {
		return token;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setToken(String token) {
		this.token = token;
	}

}