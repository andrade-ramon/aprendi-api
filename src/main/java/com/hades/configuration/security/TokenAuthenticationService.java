package com.hades.configuration.security;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.hades.login.LoginAuthentication;
import com.hades.login.LoginInfo;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;

@Service
public class TokenAuthenticationService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TokenAuthenticationService.class);

	@Autowired
	private TokenHandler tokenHandler;

	public Authentication getAuthentication(HttpServletRequest request) {

		String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (jwtToken == null || "".equals(jwtToken)) {
			return null;
		}
		
		try {
			Optional<LoginInfo> loginInfo = tokenHandler.parseUserFromToken(jwtToken);

			if (loginInfo.isPresent()) {
				return new LoginAuthentication(loginInfo.get());
			}
		} catch (MalformedJwtException | SignatureException e) {
			LOGGER.info("Invalid Token: {}", jwtToken);
		}
		
		return null;
	}

	public void createTokenFor(LoginInfo loginInfo) {
		String token = tokenHandler.createTokenFor(loginInfo);
		loginInfo.setToken(token);
	}
	
	public Optional<LoginInfo> getUserFromToken(String token) {
		return tokenHandler.parseUserFromToken(token);
	}
}
