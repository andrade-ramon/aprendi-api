package com.qualfacul.hades.login;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;

import com.qualfacul.hades.college.College;
import com.qualfacul.hades.college.CollegeRepository;
import com.qualfacul.hades.configuration.security.TokenAuthenticationService;
import com.qualfacul.hades.user.User;
import com.qualfacul.hades.user.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class LoggedUserManagerTest {

	@Mock
	private TokenAuthenticationService tokenService;
	@Mock
	private MockHttpServletRequest request;
	@Mock
	private UserRepository userRepository;
	@Mock
	private CollegeRepository collegeRepository;
	
	private LoggedUserManager subject;
	
	private LoginInfo loginInfo;

	@Before
	public void setup() {
		subject = new LoggedUserManager(tokenService, request, userRepository, collegeRepository);
		
		loginInfo = new LoginInfo("Some login", LoginOrigin.USER);
		
		when(tokenService.getUserFromToken(Mockito.anyString())).thenReturn(Optional.of(loginInfo));
	}

	@Test
	public void shouldGetLoggedStudent() {
		User user = new User("test", "test@test.com", loginInfo);
		when(userRepository.findByEmail(loginInfo.getLogin())).thenReturn(Optional.of(user));
		
		Optional<User> student = subject.getStudent();
		
		assertTrue(student.isPresent());
	}
	
	@Test
	public void shouldGetLoggedCollege() {
		College college = new College();
		when(collegeRepository.findByLoginInfo(loginInfo)).thenReturn(Optional.of(college));
		
		Optional<College> loggedCollege = subject.getCollege();
		
		assertTrue(loggedCollege.isPresent());
	}

}
