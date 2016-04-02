package com.hades.home;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hades.annotation.PermitEndpoint;
import com.hades.login.LoggedUserManager;

@RestController
public class HomeController {
	
	@Autowired
	private LoggedUserManager loggedUser;
	
	@RequestMapping(path = "/admin", method = GET)
	public String closed() {
		return loggedUser.getUser().getEmail();
	}

	@PermitEndpoint
	@RequestMapping(path = "/", method = GET)
	public String home() {
		return "home";
	}
}
