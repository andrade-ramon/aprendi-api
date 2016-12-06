package com.qualfacul.hades.post;

import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.qualfacul.hades.college.College;
import com.qualfacul.hades.college.CollegeRepository;
import com.qualfacul.hades.exceptions.UsernameNotAllowedException;
import com.qualfacul.hades.login.LoggedUserManager;
import com.qualfacul.hades.login.LoginInfo;

@RunWith(MockitoJUnitRunner.class)
public class CollegePostSecurityAnalyzerTest {

	private CollegePostSecurityAnalyzer subject;
	
	@Mock
	private LoggedUserManager loggedUserManager;
	@Mock
	private CollegeRepository repository;
	@Mock
	private LoginInfo loginInfo;
	
	@Before
	public void setup() {
		subject = new CollegePostSecurityAnalyzer(loggedUserManager, repository);
		when(loginInfo.getLogin()).thenReturn("any login");
		when(loggedUserManager.getLoginInfo()).thenReturn(loginInfo);
	}

	@Test
	public void shouldAllowEditPostForSameCollege() {
		College college = new College();
		college.setId(123l);
		CollegePost collegePost = new CollegePost(college , "Some post");
		
		when(repository.findByCnpj(Mockito.anyString())).thenReturn(Optional.of(college));
		
		subject.validate(collegePost);
	}
	
	@Test(expected = UsernameNotAllowedException.class)
	public void shouldNotAllowEditPostForDiferentColleges() {
		College college = new College();
		college.setId(123l);
		College otherCollege = new College();
		otherCollege.setId(456l);
		
		CollegePost collegePost = new CollegePost(college , "Some post");
		
		when(repository.findByCnpj(Mockito.anyString())).thenReturn(Optional.of(otherCollege));
		
		subject.validate(collegePost);
	}

}
