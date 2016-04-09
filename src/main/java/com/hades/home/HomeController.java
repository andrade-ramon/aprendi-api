package com.hades.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.hades.annotation.Get;
import com.hades.annotation.PermitEndpoint;
import com.hades.login.LoggedUserManager;

@RestController
public class HomeController {
	
	@Autowired
	private LoggedUserManager loggedUser;
	
	@Get("/admin")
	public String closed() {
		return loggedUser.getLoginInfo().getLogin();
	}

	@PermitEndpoint
	@Get("/")
	public String home() {
		return "home";
	}
}
