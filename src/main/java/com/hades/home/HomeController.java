package com.hades.home;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hades.annotation.PermitEndpoint;

@RestController
public class HomeController {

	@RequestMapping(path = "/admin", method = GET)
	public String closed() {
		return "Ok";
	}

	@PermitEndpoint
	@RequestMapping(path = "/", method = GET)
	public String home() {
		return "home";
	}
}
