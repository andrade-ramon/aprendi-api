package com.hades.login;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hades.annotation.PermitEndpoint;
import com.hades.annotation.Post;

@Component
@RestController
public class LoginController {
	
	@Autowired
	LoginFacade loginFacade;
	
	@PermitEndpoint
	@Post("/login")
	public LoginInfoDTO login(@RequestBody @Valid LoginInfo loginInfoToAuthenticate) {
		return loginFacade.login(loginInfoToAuthenticate);
	}
}
