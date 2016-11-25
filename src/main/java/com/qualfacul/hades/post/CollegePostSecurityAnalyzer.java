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
	
	@Autowired
	private LoggedUserManager loggedUserManager;
	@Autowired
	private CollegeRepository collegeRepository;
	
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
