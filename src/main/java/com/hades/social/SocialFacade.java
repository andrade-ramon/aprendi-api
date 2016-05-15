package com.hades.social;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Component;

import com.hades.configuration.security.TokenAuthenticationService;
import com.hades.login.LoginFacade;
import com.hades.login.LoginInfo;
import com.hades.login.LoginInfoDTO;
import com.hades.login.LoginOrigin;
import com.hades.user.User;
import com.hades.user.UserDTO;
import com.hades.user.UserRepository;

@Component
public class SocialFacade {
	
	@Autowired
	LoginFacade loginFacade;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private TokenAuthenticationService tokenService;
	
	@Value("${social.defaultPassword}")
	private String defaultPassword;
	
	public String socialAuthenticator(UserProfile userProfile){
		String email, name;
		email = userProfile.getEmail();
		if (email == null){
			return "hades.facebook.accessDenied";
		}
		LoginInfo loginInfo = new LoginInfo(email, defaultPassword, LoginOrigin.USER);
		loginInfo.setPassword(defaultPassword);
		try{
			LoginInfoDTO loginInfoDTO = loginFacade.login(loginInfo);
			return loginInfoDTO.getToken();
		}catch(Exception e){
			name = userProfile.getName();
			UserDTO userDTO = new UserDTO(name, email, defaultPassword);
			User user = new User(userDTO.getName(), userDTO.getEmail());
			loginInfo = new LoginInfo(email, defaultPassword, LoginOrigin.USER);
			user.setLoginInfo(loginInfo);
			userRepository.save(user);
			tokenService.createTokenFor(loginInfo);
			return loginInfo.getToken();
		}
	}
}
