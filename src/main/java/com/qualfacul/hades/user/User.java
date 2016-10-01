package com.qualfacul.hades.user;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.MERGE;
import static javax.persistence.FetchType.LAZY;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.qualfacul.hades.college.CollegeAddress;
import com.qualfacul.hades.login.LoginInfo;
import com.qualfacul.hades.user.address.UserCollegeAddress;
import com.qualfacul.hades.user.address.UserCollegeAddressId;

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
	@JoinColumn(name = "login_info_id", referencedColumnName = "id")
	private LoginInfo loginInfo;
	
	@OneToMany(fetch = LAZY, mappedBy = "id.user", cascade = MERGE)
	@JsonIgnore
	private List<UserCollegeAddress> userCollegeAddress= new ArrayList<>();

	@Deprecated // Hibernate eyes only
	public User() {
	}

	public User(String name, String email, LoginInfo loginInfo) {
		this.name = name;
		this.email = email;
		this.loginInfo = loginInfo;
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
	
	public List<UserCollegeAddress> getUserCollegeAddress() {
		return userCollegeAddress;
	}
	
	public void setUserCollegeAddress(List<UserCollegeAddress> userCollegeAddress) {
		this.userCollegeAddress = userCollegeAddress;
	}

	public void asignCollege(CollegeAddress collegeAddress, String studentRa) {
		UserCollegeAddressId userCollegeAddressId = new UserCollegeAddressId(collegeAddress, this);
		this.userCollegeAddress.add(new UserCollegeAddress(userCollegeAddressId, studentRa));
	}
}