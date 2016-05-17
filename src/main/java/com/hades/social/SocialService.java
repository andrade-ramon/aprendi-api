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
			return "hades.facebook.accessDenied";
		}
		Optional<SocialLogin> optionalSocialLogin = socialRepository.findBySocialId(socialId);
		if (optionalSocialLogin.isPresent()){
			socialLogin = optionalSocialLogin.get();
			tokenService.createTokenFor(socialLogin);
			return socialLogin.getToken();
		}else{
			User user = new User(userProfile.getName(), userProfile.getEmail());
			userRepository.save(user);
			socialLogin = new SocialLogin(socialId, socialType, user);
			socialRepository.save(socialLogin);
			tokenService.createTokenFor(socialLogin);
			return socialLogin.getToken();
		}
		
		//Validar se o usuário já existe na tabela de social_info
		//Cadastrar o usuário
		
		
		
//		LoginInfo loginInfo = new LoginInfo(email, defaultPassword, USER);
//		loginInfo.setPassword(defaultPassword);
//		try{
//			LoginInfoDTO loginInfoDTO = loginService.login(loginInfo);
//			return loginInfoDTO.getToken();
//		}catch(Exception e){
//			String name = userProfile.getName();
//			UserDTO userDTO = new UserDTO(name, email, defaultPassword);
//			User user = new User(userDTO.getName(), userDTO.getEmail());
//			loginInfo = new LoginInfo(email, defaultPassword, LoginOrigin.USER);
//			user.setLoginInfo(loginInfo);
//			userRepository.save(user);
//			tokenService.createTokenFor(loginInfo);
//			return loginInfo.getToken();
//		}
	}
}
