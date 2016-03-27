package com.hades.user;

import com.hades.user.auth.User;

public class UserAuthDTO {

	private Long id;
	private String email;
	private String token;

	public UserAuthDTO(User user) {
		this.id = user.getId();
		this.email = user.getEmail();
		this.token = user.getToken();
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getToken() {
		return token;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
