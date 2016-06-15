package com.qualfacul.hades.login;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hades.annotation.PublicEndpoint;
import com.qualfacul.hades.annotation.Post;
import com.qualfacul.hades.exceptions.LoginFailureException;

@RestController
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@PublicEndpoint
	@Post("/login")
	public LoginInfoDTO login(@RequestBody @Valid LoginInfo loginInfoToAuthenticate) {
		LoginInfoDTO dto = new LoginInfoDTO();
		
		loginService.login(loginInfoToAuthenticate, new LoginServiceCallback() {

			@Override
			public void onSuccess(LoginInfo loginInfo) {
				dto.from(loginInfo);
			}

			@Override
			public void onError(LoginInfo loginInfo) {
				throw new LoginFailureException();
			}
			
		});
		
		return dto;
	}
}
