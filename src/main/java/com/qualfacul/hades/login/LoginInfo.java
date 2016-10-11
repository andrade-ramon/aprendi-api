package com.qualfacul.hades.login;

import static com.qualfacul.hades.login.LoginOrigin.ADMIN;
import static com.qualfacul.hades.login.LoginOrigin.COLLEGE;
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
	@Column(name = "login", length = 50, unique = true)
	private String login;
	
	@Column(name = "password", length = 65, nullable = true)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "origin", length = 15, nullable = false)
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
		this(login, loginOrigin);
		this.password = password;
	}

	@PrePersist
	@PreUpdate
	void hashPassword() {
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

	public boolean isUser() {
		if (COLLEGE == loginOrigin || ADMIN == loginOrigin) {
			return false;
		}
		return true;
	}
	public boolean isFromFacebook() {
		return FACEBOOK == loginOrigin;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((loginOrigin == null) ? 0 : loginOrigin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LoginInfo other = (LoginInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (loginOrigin != other.loginOrigin)
			return false;
		return true;
	}
	
}