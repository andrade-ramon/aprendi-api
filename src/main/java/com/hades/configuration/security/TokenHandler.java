package com.hades.configuration.security;


import com.hades.user.auth.User;
import com.hades.user.auth.UserDAO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

public class TokenHandler {

	private final String secret;
	private UserDAO userDAO;

	public TokenHandler(String secret, UserDAO repository) {
		this.secret = secret;
		this.userDAO = repository;
	}

	public void createTokenFor(User user) {
		final String token = Jwts.builder()
						.setSubject(user.getUsername())
						.signWith(SignatureAlgorithm.HS256, secret)
						.compact();
		user.setToken(token);
	}

	public User parseUserFromToken(String jwtToken) {
		String username = Jwts.parser()
									.setSigningKey(secret)
									.parseClaimsJws(jwtToken)
									.getBody()
									.getSubject();
		
		return userDAO.loadUserByUsername(username);
	}
}
