package com.hades.login;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.hades.configuration.security.TokenAuthenticationService;

@Service
public class LoginService {
	
	@Autowired
	private LoginInfoRepository loginInfoRepository;
	
	@Autowired
	private TokenAuthenticationService tokenService;
	
	public void login(LoginInfo loginInfoToAuthenticate, LogingServiceCallback callback){
		Optional<LoginInfo> optionalLoginInfo = loginInfoRepository.findByLogin(loginInfoToAuthenticate.getLogin());

		if (optionalLoginInfo.isPresent()) {
			LoginInfo loginInfo = optionalLoginInfo.get();
			if (BCrypt.checkpw(loginInfoToAuthenticate.getPassword(), loginInfo.getPassword())) {
				tokenService.createTokenFor(loginInfo);
				callback.onSuccess(loginInfo);
			}
		}
		
		callback.onError(loginInfoToAuthenticate);
	}
}
