package com.qualfacul.hades.social;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

import com.qualfacul.hades.configuration.security.TokenAuthenticationService;
import com.qualfacul.hades.login.LoginInfo;
import com.qualfacul.hades.login.LoginInfoDTO;
import com.qualfacul.hades.login.LoginOrigin;
import com.qualfacul.hades.user.User;
import com.qualfacul.hades.user.UserRepository;

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
			loginInfo = new LoginInfo(email, LoginOrigin.FACEBOOK);
			User user = new User(userProfile.getName(), email, loginInfo);
			userRepository.save(user);
		}

		tokenService.createTokenFor(loginInfo);
		return new LoginInfoDTO().from(loginInfo);
	}
}
