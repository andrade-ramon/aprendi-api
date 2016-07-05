package com.qualfacul.hades.login;

import static com.qualfacul.hades.login.LoginOrigin.FACEBOOK;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Entity
@Table(name = "login_info")
public class LoginInfo {

	@Id
	@GeneratedValue
	private Long id;

	@NotEmpty(message = "Informe seu login")
	@Column(unique = true)
	private String login;
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "origin", nullable = false)
	private LoginOrigin loginOrigin;

	@Transient
	private String token;

	@Deprecated // Hibernate eyes only
	public LoginInfo() {
	}

	public LoginInfo(String login, LoginOrigin loginOrigin) {
		this.login = login;
		this.loginOrigin = loginOrigin;
	}

	public LoginInfo(String login, String password, LoginOrigin loginOrigin) {
		this.login = login;
		this.password = password;
		this.loginOrigin = loginOrigin;
	}

	@PrePersist
	@PreUpdate
	private void hashPassword() {
		this.password = password != null ? BCrypt.hashpw(password, BCrypt.gensalt()) : password;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public boolean isFromFacebook() {
		return FACEBOOK.equals(this.loginOrigin);
	}
}