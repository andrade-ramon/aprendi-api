package com.qualfacul.hades.interceptor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.web.method.HandlerMethod;

import com.qualfacul.hades.annotation.PublicEndpoint;
import com.qualfacul.hades.configuration.security.TokenAuthenticationService;
import com.qualfacul.hades.home.HomeController;
import com.qualfacul.hades.login.LoginInfo;
import com.qualfacul.hades.login.LoginOrigin;

@RunWith(MockitoJUnitRunner.class)
public class StatelessAuthenticationInterceptorTest {	
	@Mock
	private TokenAuthenticationService tokenService;
	@Mock
	private HttpServletRequest request;
	private StatelessAuthenticationInterceptor subject;
	private MockHttpServletResponse response;
	private HandlerMethod handler;
	private LoginInfo loginInfo;
	
	@Before
	public void setup() {
		subject = new StatelessAuthenticationInterceptor(tokenService);
		Method method = Arrays.asList(HomeController.class.getMethods()).stream()
						.filter(m -> !m.isAnnotationPresent(PublicEndpoint.class))
						.findFirst().get();
		handler = new HandlerMethod(HomeController.class, method);
		
		loginInfo = new LoginInfo("anyEmail@any.com", "anyPassword", LoginOrigin.USER);
		loginInfo.setToken("anyToken");
		
		response = new MockHttpServletResponse();
	}

	@Test
	public void shouldAccessAPublicEndpoint() throws Exception {
		Method method = Arrays.asList(HomeController.class.getMethods()).stream()
						.filter(m -> m.isAnnotationPresent(PublicEndpoint.class))
						.findFirst().get();
		handler = new HandlerMethod(HomeController.class, method);
		
		boolean canAccess = subject.preHandle(request, response, handler);
		
		assertTrue(canAccess);
	}
	
	@Test
	public void shouldAccessAErrorPath() throws Exception {
		when(request.getServletPath()).thenReturn("/error");
		
		boolean canAccess = subject.preHandle(request, response, handler);
		
		assertTrue(canAccess);
	}
	
	@Test
	public void shouldAccessWithAAuthorizedUser() throws Exception {
		when(tokenService.getUserFromToken(anyString())).thenReturn(Optional.of(loginInfo));
		
		boolean canAccess = subject.preHandle(request, response, handler);
		
		assertTrue(canAccess);
		assertNotEquals(response.getStatus(), UNAUTHORIZED.value());
		verify(tokenService).createTokenFor(loginInfo);
		assertEquals(response.getHeader(ACCESS_CONTROL_EXPOSE_HEADERS), AUTHORIZATION);
		assertEquals(response.getHeader(AUTHORIZATION), loginInfo.getToken());
	}
	
	@Test
	public void shouldNotAccessWithAUnauthorizedUser() throws Exception {
		when(tokenService.getUserFromToken(anyString())).thenReturn(Optional.empty());
		
		boolean canAccess = subject.preHandle(request, response, handler);
		
		assertFalse(canAccess);
		assertEquals(response.getStatus(), UNAUTHORIZED.value());
		verify(tokenService, never()).createTokenFor(loginInfo);
	}

}
