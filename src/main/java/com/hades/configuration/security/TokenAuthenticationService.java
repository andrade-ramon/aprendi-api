package com.hades.configuration.security;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;

import com.hades.user.auth.User;
import com.hades.user.auth.UserAuthentication;
import com.hades.user.auth.UserDAO;

public class TokenAuthenticationService {

	private static final String AUTH_HEADER_NAME = "Authorization";
	private static final String BEARER_VALUE = "Bearer ";

	private final TokenHandler tokenHandler;

	public TokenAuthenticationService(String secret, UserDAO userRepository) {
		tokenHandler = new TokenHandler(secret, userRepository);

	}

	public Authentication getAuthentication(HttpServletRequest request) {
		String header = request.getHeader(AUTH_HEADER_NAME);
		if (header == null || !header.startsWith(BEARER_VALUE)) {
			// throw new RuntimeException("No JWT token found in request
			// headers");
			return null;
		}

		String jwtToken = header.substring(7);
		final User user = tokenHandler.parseUserFromToken(jwtToken);

		return new UserAuthentication(user);
	}
}
