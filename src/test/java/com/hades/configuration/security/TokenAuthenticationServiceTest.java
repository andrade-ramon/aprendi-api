package com.hades.configuration.security;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.core.Authentication;

import com.hades.login.LoginAuthentication;
import com.hades.login.LoginInfo;

@RunWith(MockitoJUnitRunner.class)
public class TokenAuthenticationServiceTest {
	
	@Mock
	private TokenHandler tokenHandler;
	@Mock
	private HttpServletRequest request;
	@Mock
	private LoginInfo loginInfo;

	private TokenAuthenticationService subject;
	private String jwtToken;
	
	@Before
	public void setup() {
		jwtToken = "jwt-token";
		subject = new TokenAuthenticationService(tokenHandler);
	}

	@Test
	public void shouldReturnValidAuthenticationFromRequest() {
		when(request.getHeader(AUTHORIZATION)).thenReturn(jwtToken);
		when(tokenHandler.parseUserFromToken(jwtToken)).thenReturn(Optional.of(loginInfo));
		
		Authentication authentication = subject.getAuthentication(request);
		
		verify(tokenHandler).parseUserFromToken(Mockito.anyString());
		assertTrue(authentication instanceof LoginAuthentication);
	}
	
	@Test
	public void shouldReturnNullWhenHasAuthenticationFromRequestAndLoginInfoIsAbsent() {
		when(request.getHeader(AUTHORIZATION)).thenReturn(jwtToken);
		when(tokenHandler.parseUserFromToken(jwtToken)).thenReturn(Optional.empty());
		
		Authentication authentication = subject.getAuthentication(request);
		
		verify(tokenHandler).parseUserFromToken(Mockito.anyString());
		assertNull(authentication);
	}

	@Test
	public void shouldReturnNullWhenRequestDoesNotHaveAuthenticationHeader() {
		assertNull(subject.getAuthentication(request));
		
		verify(tokenHandler, never()).parseUserFromToken(Mockito.anyString());
	}
}
