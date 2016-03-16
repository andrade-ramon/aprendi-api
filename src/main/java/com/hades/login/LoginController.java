package com.hades.login;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hades.configuration.security.TokenAuthenticationService;
import com.hades.user.UserView;
import com.hades.user.auth.User;
import com.hades.user.auth.UserDAO;

@RestController
public class LoginController {

	@Value("${jwt.secret}")
	private String SECRET;
	
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private TokenAuthenticationService tokenService;
	
	@RequestMapping(path = "/login",method = POST)
	public UserView logar(@RequestParam(name = "email", required = true) String email, @RequestParam(name = "password", required = true) String password) {
		User user = userDAO.findBy(email, password);
		
		tokenService.createTokenFor(user);
		
		return new UserView(user);
	}
}
