package com.hades.login;

import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

public class LoginAuthentication implements Authentication{

	private static final long serialVersionUID = 1L;
	private String username;
	private String password;
	private boolean authentication = true;

	public LoginAuthentication(LoginInfo loginInfo) {
		this.username = loginInfo.getLogin();
		this.password = loginInfo.getPassword();
	}

	@Override
	public String getName() {
		return username;
	}
	
	@Override
	public Object getCredentials() {
		return this.password;
	}

	@Override
	public Object getDetails() {
		return null;
	}

	@Override
	public Object getPrincipal() {
		return null;
	}

	@Override
	public boolean isAuthenticated() {
		return authentication;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.authentication = isAuthenticated;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

}
