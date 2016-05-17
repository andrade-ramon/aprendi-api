package com.hades.configuration.security;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.hades.login.LoginAuthentication;
import com.hades.login.LoginInfo;
import com.hades.social.SocialLogin;

@Service
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
	
	public void createTokenFor(SocialLogin socialLogin) {
		String token = tokenHandler.createTokenFor(socialLogin);
		socialLogin.setToken(token);
	}

	public Optional<LoginInfo> getUserFromToken(String token) {
		return tokenHandler.parseUserFromToken(token);
	}
}
