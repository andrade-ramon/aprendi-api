package com.qualfacul.hades.home;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.Get;
import com.qualfacul.hades.annotation.PermitEndpoint;
import com.qualfacul.hades.login.LoggedUserManager;

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
