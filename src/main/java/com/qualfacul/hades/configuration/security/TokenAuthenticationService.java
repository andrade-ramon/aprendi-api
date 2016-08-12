package com.qualfacul.hades.configuration.security;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.Calendar;
import java.util.Date;
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
	
	private final static int ONE_DAY_IN_MINUTES_EXPIRATION_TIME = 60*24;

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
		createToken(loginInfo, null);
	}
	
	public void createTemporaryTokenFor(LoginInfo loginInfo){
		Calendar expirationDate = Calendar.getInstance();
		expirationDate.add(Calendar.MINUTE, ONE_DAY_IN_MINUTES_EXPIRATION_TIME);
		createToken(loginInfo, expirationDate.getTime());
	}
	
	public String createTokenFor(LoginInfo loginInfo, Date expirationDate){
		return tokenHandler.createTokenFor(loginInfo, expirationDate);
	}

	public Optional<LoginInfo> getUserFromToken(String token) {
		return tokenHandler.parseUserFromToken(token);
	}
	
	private void createToken(LoginInfo loginInfo, Date expirationDate) {
		String token = tokenHandler.createTokenFor(loginInfo, expirationDate);
		loginInfo.setToken(token);
	}
}
