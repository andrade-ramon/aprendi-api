package com.hades.login;

public class LoginInfoDTO {
	private String login;
	private String token;

	public LoginInfoDTO(LoginInfo loginInfo) {
		this.login = loginInfo.getLogin();
		this.token = loginInfo.getToken();
	}

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

}
