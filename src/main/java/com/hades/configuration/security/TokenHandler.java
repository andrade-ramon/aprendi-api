package com.hades.configuration.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hades.login.LoginInfo;
import com.hades.login.LoginInfoDAO;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
class TokenHandler {
	@Value("${jwt.secret}")
	private String secret;

	@Autowired
	private LoginInfoDAO repository;

	public String createTokenFor(LoginInfo loginInfo) {
		return Jwts.builder().setSubject(loginInfo.getLogin()).signWith(SignatureAlgorithm.HS256, secret).compact();
	}

	public Optional<LoginInfo> parseUserFromToken(String jwtToken) {
		String login = "";
		try {
			login = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken).getBody().getSubject();
		} catch (JwtException | IllegalArgumentException e) {
			return Optional.empty();
		}
		
		return repository.findBy(login);
	}
}
