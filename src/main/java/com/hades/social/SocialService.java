package com.hades.social;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

import com.hades.configuration.security.TokenAuthenticationService;
import com.hades.login.LoginInfo;
import com.hades.login.LoginInfoDTO;
import com.hades.login.LoginOrigin;
import com.hades.user.User;
import com.hades.user.UserRepository;

@Service
public class SocialService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TokenAuthenticationService tokenService;

	public LoginInfoDTO authenticate(UserProfile userProfile, LoginOrigin loginOrigin) {
		String email = userProfile.getEmail();
		Optional<User> optionalUser = userRepository.findByEmail(email);

		LoginInfo loginInfo;
		if (optionalUser.isPresent()) {
			loginInfo = optionalUser.get().getLoginInfo();
		} else {
			loginInfo = new LoginInfo(email, null, LoginOrigin.FACEBOOK);
			User user = new User(userProfile.getName(), email, loginInfo);
			userRepository.save(user);
		}

		tokenService.createTokenFor(loginInfo);
		LoginInfoDTO loginInfoDTO = new LoginInfoDTO(loginInfo);
		return loginInfoDTO;
	}
}
