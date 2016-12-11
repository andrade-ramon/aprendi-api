package com.qualfacul.hades.post;

import org.springframework.beans.factory.annotation.Autowired;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.college.College;
import com.qualfacul.hades.college.CollegeRepository;
import com.qualfacul.hades.exceptions.UsernameNotAllowedException;
import com.qualfacul.hades.login.LoggedUserManager;
import com.qualfacul.hades.login.LoginInfo;

@WebComponent
public class CollegePostSecurityAnalyzer {
	
	private LoggedUserManager loggedUserManager;
	private CollegeRepository collegeRepository;
	
	@Autowired
	public CollegePostSecurityAnalyzer(LoggedUserManager loggedUserManager, CollegeRepository collegeRepository) {
		this.loggedUserManager = loggedUserManager;
		this.collegeRepository = collegeRepository;
	}
	
	public void validate(CollegePost collegePost){
		LoginInfo loginInfo = loggedUserManager.getLoginInfo();
		
		College loggedCollege = collegeRepository.findByCnpj(loginInfo.getLogin())
													.orElseThrow(UsernameNotAllowedException::new);
		
		if (!collegePost.getCollege().equals(loggedCollege)){
			throw new UsernameNotAllowedException();
		}
		
		return;
	}
}
