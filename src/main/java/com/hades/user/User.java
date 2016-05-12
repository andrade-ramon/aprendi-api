package com.hades.user;

import static javax.persistence.CascadeType.ALL;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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

	@Column(name = "born_date")
	@Temporal(TemporalType.DATE)
	private Calendar bornDate;

	@Column(name = "accept_email", columnDefinition = "TINYINT(1)")
	private Boolean acceptEmail;

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

	@PrePersist
	@PreUpdate
	public void prePersist() {
		if (isAcceptEmail() == null) {
			setAcceptEmail(true);
		}
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

	public Calendar getBornDate() {
		return bornDate;
	}

	public void setBornDate(Calendar bornDate) {
		this.bornDate = bornDate;
	}

	public Boolean isAcceptEmail() {
		return acceptEmail;
	}

	public void setAcceptEmail(Boolean acceptEmail) {
		this.acceptEmail = acceptEmail;
	}

	public LoginInfo getLoginInfo() {
		return loginInfo;
	}

	public void setLoginInfo(LoginInfo loginInfo) {
		this.loginInfo = loginInfo;
	}

}