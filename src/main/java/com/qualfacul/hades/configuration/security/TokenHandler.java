package com.qualfacul.hades.configuration.security;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.login.LoginInfo;
import com.qualfacul.hades.login.LoginInfoRepository;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@WebComponent
class TokenHandler {

	@Value("${jwt.secret}")
	private String secret;

	@Autowired
	private LoginInfoRepository loginInfoRepository;

	public String createTokenFor(LoginInfo loginInfo) {
		return Jwts.builder().setSubject(loginInfo.getLogin()).signWith(SignatureAlgorithm.HS256, secret).compact();
	}
	
	public String createTokenFor(LoginInfo loginInfo, Date expirationDate){
		return Jwts.builder().setSubject(loginInfo.getLogin()).signWith(SignatureAlgorithm.HS256, secret).setExpiration(expirationDate).compact();
	}

	public Optional<LoginInfo> parseUserFromToken(String jwtToken) {
		String login = "";
		try {
			login = Jwts.parser().setSigningKey(secret).parseClaimsJws(jwtToken).getBody().getSubject();
		} catch (JwtException | IllegalArgumentException e) {
			return Optional.empty();
		}

		return loginInfoRepository.findByLogin(login);
	}
}
