package com.qualfacul.hades.login;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.college.College;
import com.qualfacul.hades.college.CollegeRepository;
import com.qualfacul.hades.configuration.security.TokenAuthenticationService;
import com.qualfacul.hades.user.User;
import com.qualfacul.hades.user.UserRepository;

@WebComponent
public class LoggedUserManager {

	@Autowired
	private TokenAuthenticationService tokenService;
	@Autowired
	private HttpServletRequest request;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private CollegeRepository collegeRepository;

	public LoginInfo getLoginInfo() {
		return loginInfo();
	}

	public Optional<User> getStudent() {
		LoginInfo loginInfo = loginInfo();
		if(loginInfo() != null) {
			return userRepository.findByEmail(loginInfo.getLogin());
		}
		return Optional.empty();
	}
	
	private LoginInfo loginInfo() {
		String token = request.getHeader(AUTHORIZATION);
		
		Optional<LoginInfo> loginInfo = tokenService.getUserFromToken(token);
		if (loginInfo.isPresent()) {
			return loginInfo.get();
		}
		return null;
	}

	public Optional<College> getCollege() {
		LoginInfo loginInfo = loginInfo();
		if(loginInfo() != null) {
			return collegeRepository.findByLoginInfo(loginInfo);
		}
		return Optional.empty();
	}
}
