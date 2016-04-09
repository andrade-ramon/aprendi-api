package com.hades.login;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.hades.annotation.Post;
import com.hades.annotation.PermitEndpoint;
import com.hades.configuration.security.TokenAuthenticationService;
import com.hades.exceptions.LoginFailureException;

@RestController
public class LoginController {

	@Autowired
	private LoginInfoDAO loginInfoDAO;

	@Autowired
	private TokenAuthenticationService tokenService;

	@PermitEndpoint
	@Post("/login")
	public LoginInfoDTO login(@RequestBody @Valid LoginInfo loginInfoToAuthenticate) {

		Optional<LoginInfo> loginInfo = loginInfoDAO.findBy(loginInfoToAuthenticate.getLogin(),
				loginInfoToAuthenticate.getPassword());

		if (loginInfo.isPresent()) {
			tokenService.createTokenFor(loginInfo.get());
			LoginInfoDTO loginInfoDTO = new LoginInfoDTO(loginInfo.get());
			return loginInfoDTO;
		}

		throw new LoginFailureException();
	}
}
