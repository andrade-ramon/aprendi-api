package com.hades.login;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hades.configuration.security.TokenAuthenticationService;
import com.hades.user.auth.User;

@Component
public class LoggedUserManager {
	
	@Autowired
	private TokenAuthenticationService tokenService;
	@Autowired
	private HttpServletRequest request;
	
	public User getUser() {
		String token = request.getHeader(AUTHORIZATION);
		
		return tokenService.getUserFromToken(token).get();
	}
}
