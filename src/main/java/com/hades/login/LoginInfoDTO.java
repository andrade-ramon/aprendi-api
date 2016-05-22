package com.hades.login;

public class LoginInfoDTO {

	private String login;
	private String token;

	public String getLogin() {
		return login;
	}

	public String getToken() {
		return token;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public LoginInfoDTO from(LoginInfo loginInfo) {
		this.setLogin(loginInfo.getLogin());
		this.setToken(loginInfo.getToken());
		return this;
	}

}
