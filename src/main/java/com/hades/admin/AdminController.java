package com.hades.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hades.annotation.Post;
import com.hades.configuration.security.TokenAuthenticationService;
import com.hades.login.LoginInfo;
import com.hades.login.LoginInfoDTO;

@RestController
public class AdminController {
	
	@Autowired
	private AdminDAO adminDAO;

	@Autowired
	private TokenAuthenticationService tokenService;

	@Post("/admin/register")
	@ResponseStatus(HttpStatus.CREATED)
	public LoginInfoDTO register(@Valid @RequestBody AdminDTO adminDTO) {
		LoginInfo loginInfo = adminDAO.createFrom(adminDTO);
		tokenService.createTokenFor(loginInfo);

		LoginInfoDTO loginInfoDTO = new LoginInfoDTO(loginInfo);
		return loginInfoDTO;
	}
}