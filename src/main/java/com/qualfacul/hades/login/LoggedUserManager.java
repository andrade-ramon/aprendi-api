package com.qualfacul.hades.login;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.configuration.security.TokenAuthenticationService;
import com.qualfacul.hades.exceptions.InvalidTokenException;

@WebComponent
public class LoggedUserManager {

	@Autowired
	private TokenAuthenticationService tokenService;
	@Autowired
	private HttpServletRequest request;

	public LoginInfo getLoginInfo() {
		String token = request.getHeader(AUTHORIZATION);

		return tokenService.getUserFromToken(token).orElseThrow(InvalidTokenException::new);
	}
}
