package com.hades.login;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
@Table(name = "login_info")
public class LoginInfo {

	@Id
	@NotEmpty(message = "Informe seu login")
	private String login;
	
	@NotEmpty(message = "Informe sua senha")
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "login_origin")
	private LoginOrigin loginOrigin;
	
	@Transient
	private String token;

	@Deprecated // Hibernate eyes only
	public LoginInfo() {
	}

	public LoginInfo(String login, String password, LoginOrigin loginOrigin) {
		this.login = login;
		this.password = (password != null ? BCrypt.hashpw(password, BCrypt.gensalt()) : "");
		this.loginOrigin = loginOrigin;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
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

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}