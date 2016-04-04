package com.hades.login;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	@RequestMapping(path = "/login", method = POST)
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
