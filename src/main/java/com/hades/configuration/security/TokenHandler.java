package com.hades.configuration.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hades.user.auth.User;
import com.hades.user.auth.UserDAO;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
class TokenHandler {

	@Value("${jwt.secret}")
	private String secret;
	
	@Autowired
	private UserDAO repository;

	public String createTokenFor(User user) {
		return Jwts.builder()
						.setSubject(user.getUsername())
						.signWith(SignatureAlgorithm.HS256, secret)
						.compact();
	}

	public User parseUserFromToken(String jwtToken) {
		String username = Jwts.parser()
									.setSigningKey(secret)
									.parseClaimsJws(jwtToken)
									.getBody()
									.getSubject();
		
		return repository.findBy(username);
	}
}
