package com.qualfacul.hades.configuration.security;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;

import com.qualfacul.hades.annotation.WebService;
import com.qualfacul.hades.login.LoginAuthentication;
import com.qualfacul.hades.login.LoginInfo;

@WebService
public class TokenAuthenticationService {

	private TokenHandler tokenHandler;

	@Autowired
	public TokenAuthenticationService(TokenHandler tokenHandler) {
		this.tokenHandler = tokenHandler;
	}

	public Authentication getAuthentication(HttpServletRequest request) {

		String jwtToken = request.getHeader(HttpHeaders.AUTHORIZATION);

		if (isEmpty(jwtToken)) {
			return null;
		}

		Optional<LoginInfo> loginInfo = tokenHandler.parseUserFromToken(jwtToken);

		if (loginInfo.isPresent()) {
			return new LoginAuthentication(loginInfo.get());
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
