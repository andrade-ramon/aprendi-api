package com.hades.login;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hades.annotation.PermitEndpoint;
import com.hades.configuration.security.TokenAuthenticationService;
import com.hades.exceptions.LoginFailureException;
import com.hades.user.UserAuthDTO;
import com.hades.user.auth.User;
import com.hades.user.auth.UserDAO;

@RestController
public class LoginController {

	@Autowired
	private UserDAO userDAO;

	@Autowired
	private TokenAuthenticationService tokenService;

	@PermitEndpoint
	@RequestMapping(path = "/login", method = POST)
	public UserAuthDTO login(@RequestBody User userToAuthenticate) {
		Optional<User> user = userDAO.findBy(userToAuthenticate.getEmail(), userToAuthenticate.getPassword());
		if (user.isPresent()) {
			tokenService.createTokenFor(user.get());
			UserAuthDTO userAuthDTO = new UserAuthDTO(user.get());
			return userAuthDTO;
		}

		throw new LoginFailureException();
	}
}
