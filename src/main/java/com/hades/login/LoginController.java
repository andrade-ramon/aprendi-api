package com.hades.login;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hades.annotation.PermitEndpoint;
import com.hades.annotation.Post;

@RestController
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	@PermitEndpoint
	@Post("/login")
	public LoginInfoDTO login(@RequestBody @Valid LoginInfo loginInfoToAuthenticate) {
		loginService.login(loginInfoToAuthenticate, new LogingServiceCallback() {

			@Override
			public LoginInfoDTO onSuccess(LoginInfo loginInfo) {
				return new LoginInfoDTO(loginInfo);
			}

			@Override
			public void onError(LoginInfo loginInfo) {
				//LOGA QUE DEU ERROR ,troca status E FAZ OUTRA COISA
			}
			
		});
		return null;
	}
}
