package com.hades.configuration.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hades.user.auth.User;
import com.hades.user.auth.UserDAO;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
class TokenHandler {
	@Value("${jwt.secret}")
	private String secret;

	@Autowired
	private UserDAO repository;

	public String createTokenFor(User user) {
		return Jwts.builder().setSubject(user.getUsername()).signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public Optional<User> parseUserFromToken(String jwtToken) {
		String username = "";
		try {
			username = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken).getBody().getSubject();
		} catch (JwtException | IllegalArgumentException e) {
			return Optional.empty();
		}
		return repository.findBy(username);
	}
}
