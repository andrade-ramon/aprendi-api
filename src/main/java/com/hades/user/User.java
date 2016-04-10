package com.hades.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import com.hades.login.LoginOrigin;

@Entity
@Table(name = "user")
public class User {
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	private String name;
	
	@NotNull
	@Column(unique = true)
	private String email;
	
	@Transient
	private LoginOrigin loginOrigin;
	
	@Deprecated // Hibernate eyes only
	public User() {
	}

	public User(String name, String email, LoginOrigin loginOrigin) {
		this.name = name;
		this.email = email;
		this.loginOrigin = loginOrigin;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public LoginOrigin getLoginOrigin() {
		return loginOrigin;
	}

	public void setLoginOrigin(LoginOrigin loginOrigin) {
		this.loginOrigin = loginOrigin;
	}
}