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

	private TokenAuthenticationService tokenService;
	private HttpServletRequest request;
	private UserRepository userRepository;
	private CollegeRepository collegeRepository;

	@Autowired
	public LoggedUserManager(TokenAuthenticationService tokenService, HttpServletRequest request, 
					UserRepository userRepository, CollegeRepository collegeRepository) {
		this.tokenService = tokenService;
		this.request = request;
		this.userRepository = userRepository;
		this.collegeRepository = collegeRepository;
	}

	public LoginInfo getLoginInfo() {
		return getFromToken().get();
	}

	public Optional<User> getStudent() {
		return getFromToken()
						.map(loginInfo -> userRepository.findByEmail(loginInfo.getLogin()))
						.orElse(Optional.empty());
	}
	
	public Optional<College> getCollege() {
		return getFromToken()
						.map(loginInfo -> collegeRepository.findByLoginInfo(loginInfo))
						.orElse(Optional.empty());
	}
	
	private Optional<LoginInfo> getFromToken() {
		String token = request.getHeader(AUTHORIZATION);
		
		return tokenService.getUserFromToken(token);
	}
}
