package com.hades.user;

import static javax.persistence.CascadeType.ALL;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.hades.login.LoginInfo;

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
	
	@OneToOne(cascade = ALL)
	@JoinColumn(name = "login_info_id", referencedColumnName = "id", nullable = false)
	private LoginInfo loginInfo;
	
	@Deprecated // Hibernate eyes only
	public User() {
	}

	public User(String name, String email) {
		this.name = name;
		this.email = email;
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

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

}