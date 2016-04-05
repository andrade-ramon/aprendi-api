package com.hades.login;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hades.configuration.security.TokenAuthenticationService;

@Component
public class LoggedUserManager {

	@Autowired
	private TokenAuthenticationService tokenService;
	@Autowired
	private HttpServletRequest request;

	public LoginInfo getLoginInfo() {
		String token = request.getHeader(AUTHORIZATION);

		return tokenService.getUserFromToken(token).get();
	}
}
