package com.hades.user;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hades.annotation.PermitEndpoint;
import com.hades.annotation.Post;
import com.hades.configuration.security.TokenAuthenticationService;
import com.hades.login.LoggedUserManager;
import com.hades.login.LoginInfo;
import com.hades.login.LoginInfoDTO;
import com.hades.login.LoginOrigin;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenAuthenticationService tokenService;
	
	@Autowired
	private LoggedUserManager loggedUser;

	@Transactional
	@PermitEndpoint
	@Post("/register")
	@ResponseStatus(CREATED)
	public LoginInfoDTO register(@Valid @RequestBody UserDTO userDTO) {
		LoginInfo loginInfo = new LoginInfo(userDTO.getEmail(), userDTO.getPassword(), LoginOrigin.USER);
		User user = new User(userDTO.getName(), userDTO.getEmail());
		user.setLoginInfo(loginInfo);
		userRepository.save(user);
		
		tokenService.createTokenFor(loginInfo);

		return new LoginInfoDTO(loginInfo);
	}
	
	@Post("/user")
	@ResponseStatus(OK)
	public void editPersonalInfo(@Valid @RequestBody UserInfoDTO userProfileDTO){		
		User user = userRepository.findByEmail(loggedUser.getLoginInfo().getLogin());
		user.setAcceptEmail(userProfileDTO.getAcceptEmail());
		user.setBornDate(userProfileDTO.getBornDate());
		user.setName(userProfileDTO.getName());
		
		userRepository.save(user);
	}
	
}