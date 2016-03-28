package com.hades.login;

public class LoginInfoDTO {

	private Long id;
	private String login;
	private String token;

	public LoginInfoDTO(LoginInfo user) {
		this.id = user.getId();
		this.login = user.getLogin();
		this.token = user.getToken();
	}

	public Long getId() {
		return id;
	}

	public String getLogin() {
		return login;
	}

	public String getToken() {
		return token;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
