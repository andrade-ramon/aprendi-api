package com.hades.configuration.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.hades.user.auth.User;
import com.hades.user.auth.UserAuthentication;

@Service
public class TokenAuthenticationService {

	private static final String AUTH_HEADER_NAME = "Authorization";
	private static final String BEARER_VALUE = "Bearer ";

	@Autowired
	private TokenHandler tokenHandler;

	public Authentication getAuthentication(HttpServletRequest request) {
		String header = request.getHeader(AUTH_HEADER_NAME);
		
		if (header == null || !header.startsWith(BEARER_VALUE)) {
			return null;
		}

		String jwtToken = header.substring(7);
		final User user = tokenHandler.parseUserFromToken(jwtToken);

		return new UserAuthentication(user);
	}

	public void createTokenFor(User user) {
		String token = tokenHandler.createTokenFor(user);
		
		user.setToken(token);
	}
}
