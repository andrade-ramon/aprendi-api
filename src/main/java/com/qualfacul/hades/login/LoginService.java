package com.qualfacul.hades.login;

import static org.springframework.util.StringUtils.isEmpty;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.qualfacul.hades.configuration.security.TokenAuthenticationService;

@Service
public class LoginService {

	private LoginInfoRepository loginInfoRepository;

	private TokenAuthenticationService tokenService;
	
	@Autowired
	public LoginService(LoginInfoRepository loginInfoRepository, TokenAuthenticationService tokenService) {
		this.loginInfoRepository = loginInfoRepository;
		this.tokenService = tokenService;
	}

	public void login(LoginInfo loginInfoToAuthenticate, LoginServiceCallback callback) {
		Optional<LoginInfo> optionalLoginInfo = loginInfoRepository.findByLogin(loginInfoToAuthenticate.getLogin());
		
		if (optionalLoginInfo.isPresent()) {
			LoginInfo loginInfo = optionalLoginInfo.get();
			
			if (isEmpty(loginInfoToAuthenticate.getPassword()) || isEmpty(loginInfo.getPassword()) ) {
				callback.onError(loginInfoToAuthenticate);
				return;
			}

			if (BCrypt.checkpw(loginInfoToAuthenticate.getPassword(), loginInfo.getPassword())) {
				tokenService.createTokenFor(loginInfo);
				callback.onSuccess(loginInfo);
				return;
			}
		}
		callback.onError(loginInfoToAuthenticate);
	}
}
