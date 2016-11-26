package com.qualfacul.hades.interceptor;

import static java.util.Arrays.stream;
import static org.junit.Assert.assertTrue;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;

import com.qualfacul.hades.annotation.OnlyAdmins;
import com.qualfacul.hades.annotation.OnlyColleges;
import com.qualfacul.hades.annotation.OnlyStudents;
import com.qualfacul.hades.college.CollegeController;
import com.qualfacul.hades.conversation.ConversationController;
import com.qualfacul.hades.exceptions.OnlyAdminsCanAccessException;
import com.qualfacul.hades.exceptions.OnlyCollegesCanAccessException;
import com.qualfacul.hades.exceptions.OnlyStudentsCanAccessException;
import com.qualfacul.hades.login.LoggedUserManager;

@RunWith(MockitoJUnitRunner.class)
public class LoggedUserValidatorInterceptorTest {
	
	private LoggedUserValidatorInterceptor subject;
	@Mock(answer = RETURNS_DEEP_STUBS)
	private LoggedUserManager loggedUserManager;
	private MockHttpServletRequest request;
	private MockHttpServletResponse response;
	
	@Before
	public void setup() {
		subject = new LoggedUserValidatorInterceptor(loggedUserManager);
	}

	@Test
	public void shouldAccessWhenLoggedUserIsAUser() throws Exception {
		when(loggedUserManager.getLoginInfo().isUser()).thenReturn(true);
		when(loggedUserManager.getLoginInfo().isAdmin()).thenReturn(false);
		
		Method method = stream(ConversationController.class.getMethods())
						.filter(m -> m.isAnnotationPresent(OnlyStudents.class))
						.findFirst().get();
		
		HandlerMethod handler = new HandlerMethod(ConversationController.class, method);
		
		boolean canAccess = subject.preHandle(request, response, handler);
		
		assertTrue(canAccess);
	}
	
	@Test(expected = OnlyStudentsCanAccessException.class)
	public void shouldNotAccessWhenLoggedUserIsNotAStudent() throws Exception {
		when(loggedUserManager.getLoginInfo().isUser()).thenReturn(false);
		when(loggedUserManager.getLoginInfo().isAdmin()).thenReturn(false);
		
		Method method = stream(ConversationController.class.getMethods())
						.filter(m -> m.isAnnotationPresent(OnlyStudents.class))
						.findFirst().get();
		
		HandlerMethod handler = new HandlerMethod(ConversationController.class, method);
		
		subject.preHandle(request, response, handler);
	}
	
	@Test(expected = OnlyAdminsCanAccessException.class)
	public void shouldNotAccessWhenLoggedUserIsNotAnAdmin() throws Exception {
		when(loggedUserManager.getLoginInfo().isUser()).thenReturn(false);
		
		Method method = stream(CollegeController.class.getMethods())
						.filter(m -> m.isAnnotationPresent(OnlyAdmins.class))
						.findFirst().get();
		
		HandlerMethod handler = new HandlerMethod(CollegeController.class, method);
		
		subject.preHandle(request, response, handler);
	}
	
	@Test(expected = OnlyCollegesCanAccessException.class)
	public void shouldNotAccessWhenLoggedUserIsNotACollege() throws Exception {
		when(loggedUserManager.getLoginInfo().isUser()).thenReturn(false);
		
		Method method = stream(CollegeController.class.getMethods())
						.filter(m -> m.isAnnotationPresent(OnlyColleges.class))
						.findFirst().get();
		
		HandlerMethod handler = new HandlerMethod(CollegeController.class, method);
		
		subject.preHandle(request, response, handler);
	}
}
