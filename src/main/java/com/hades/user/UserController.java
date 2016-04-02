package com.hades.user;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

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
public class UserController {
	@Autowired
	private UserDAO userDAO;

	@Autowired
	private TokenAuthenticationService tokenService;

	@PermitEndpoint
	@RequestMapping(path = "/register", method = POST)
	public ResponseEntity<LoginInfoDTO> register(@RequestBody UserDTO userDTO) {
		LoginInfo loginInfo = userDAO.createUserAndLoginFor(userDTO);
		tokenService.createTokenFor(loginInfo);
		
		LoginInfoDTO loginInfoDTO = new LoginInfoDTO(loginInfo);
		return new ResponseEntity<LoginInfoDTO>(loginInfoDTO, HttpStatus.CREATED);
	}

}
