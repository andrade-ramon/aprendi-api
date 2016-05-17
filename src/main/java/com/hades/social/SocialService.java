package com.hades.social;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.UserProfile;
import org.springframework.stereotype.Service;

import com.hades.configuration.security.TokenAuthenticationService;
import com.hades.user.User;
import com.hades.user.UserRepository;

@Service
public class SocialService {
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SocialLoginRepository socialRepository;
	
	@Autowired
	private TokenAuthenticationService tokenService;
	
	@Value("${social.defaultPassword}")
	private String defaultPassword;
	
	public String authenticate(UserProfile userProfile, SocialType socialType){
		SocialLogin socialLogin;
		String socialId = userProfile.getEmail();
		System.out.println(socialId);
		if (socialId == null){
			return "hades.social.accessDenied";
		}
		Optional<SocialLogin> optionalSocialLogin = socialRepository.findBySocialId(socialId);
		if (optionalSocialLogin.isPresent()){ //user has been found
			socialLogin = optionalSocialLogin.get();
			tokenService.createTokenFor(socialLogin);
			return socialLogin.getToken();
		}else{
			Optional<User> optionalUser = userRepository.findByEmail(userProfile.getEmail());
			User user;
			if (optionalUser.isPresent()){
				//User exists, but don't have a social login
				user = optionalUser.get();
			}else{
				//User do not exists, so create him	
				user = new User(userProfile.getName(), userProfile.getEmail());
				userRepository.save(user);
			}
			socialLogin = new SocialLogin(socialId, socialType, user);
			socialRepository.save(socialLogin);
			tokenService.createTokenFor(socialLogin);
			return socialLogin.getToken();
		}
	}
}
