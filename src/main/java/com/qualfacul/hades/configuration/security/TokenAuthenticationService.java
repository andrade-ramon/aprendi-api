package com.qualfacul.hades.configuration.security;

import java.util.Calendar;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.qualfacul.hades.annotation.WebService;
import com.qualfacul.hades.login.LoginInfo;

@WebService
public class TokenAuthenticationService {
	
	private final static int ONE_DAY_IN_MINUTES_EXPIRATION_TIME = 60*24;

	private TokenHandler tokenHandler;
	
	@Autowired
	public TokenAuthenticationService(TokenHandler tokenHandler) {
		this.tokenHandler = tokenHandler;
	}

	public void createTokenFor(LoginInfo loginInfo){
		Calendar expirationDate = Calendar.getInstance();
		expirationDate.add(Calendar.MINUTE, ONE_DAY_IN_MINUTES_EXPIRATION_TIME);
		String token = tokenHandler.createTokenFor(loginInfo, expirationDate.getTime());
		loginInfo.setToken(token);
	}
	
	public Optional<LoginInfo> getUserFromToken(String token) {
		return tokenHandler.parseUserFromToken(token);
	}
	
}
