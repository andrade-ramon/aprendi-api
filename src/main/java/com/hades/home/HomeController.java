package com.hades.home;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hades.annotation.PermitEndpoint;
import com.hades.login.LoggedUser;

@RestController
public class HomeController {
	
	@Autowired
	private LoggedUser loggedUser;
	
	@RequestMapping(path = "/admin", method = GET)
	public String closed() {
		if (loggedUser.getUser().isPresent()) {
			return loggedUser.getUser().get().getEmail();
		}
		return null;
	}

	@PermitEndpoint
	@RequestMapping(path = "/", method = GET)
	public String home() {
		return "home";
	}
}
