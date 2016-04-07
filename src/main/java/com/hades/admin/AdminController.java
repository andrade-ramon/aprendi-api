package com.hades.admin;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hades.annotation.PermitEndpoint;
import com.hades.configuration.security.TokenAuthenticationService;
import com.hades.login.LoginInfo;
import com.hades.login.LoginInfoDTO;

@RestController
public class AdminController {
	@Autowired
	private AdminDAO adminDAO;
	
	@Autowired
	private TokenAuthenticationService tokenService;
	
	@PermitEndpoint
	@RequestMapping(path = "/adminRegister", method = POST)
	public ResponseEntity<LoginInfoDTO> register(@Valid @RequestBody AdminDTO adminDTO) {
		LoginInfo loginInfo = adminDAO.createAdminFrom(adminDTO);
		tokenService.createTokenFor(loginInfo);

		LoginInfoDTO loginInfoDTO = new LoginInfoDTO(loginInfo);
		return new ResponseEntity<LoginInfoDTO>(loginInfoDTO, HttpStatus.CREATED);
	}
	
}
