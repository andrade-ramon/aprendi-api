package com.hades.login;

import static com.hades.login.LoginOrigin.USER;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.hades.configuration.security.TokenAuthenticationService;

@RunWith(MockitoJUnitRunner.class)
public class LoginServiceTest {
	
	private LoginService subject;
	
	@Mock
	private LoginInfoRepository repository;
	@Mock
	private TokenAuthenticationService tokenService;
	@Mock
	private LoginServiceCallback callback;

	private String anyLogin;
	private String anyPassword;
	
	@Before
	public void setup() {
		subject = new LoginService(repository, tokenService);
		anyLogin = "test@test.com";
		anyPassword = "test";
	}

	@Test
	public void shouldLoginSuccessfullyWhenUserExistsAndPasswordMatches() {
		LoginInfo loginInfo = new LoginInfo(anyLogin, anyPassword, USER);
		
		LoginInfo correctLoginInfo = new LoginInfo(anyLogin, anyPassword, USER);
		correctLoginInfo.hashPassword();
		
		when(repository.findByLogin(anyLogin)).thenReturn(Optional.of(correctLoginInfo));
		subject.login(loginInfo, callback);
		
		verify(callback).onSuccess(any(LoginInfo.class));
		verify(callback, never()).onError(loginInfo);
		verify(tokenService).createTokenFor(correctLoginInfo);
	}
	
	@Test
	public void shouldFailLoginWhenUserExistsButPasswordDoesNotMatches() {
		LoginInfo loginInfo = new LoginInfo(anyLogin, anyPassword, USER);
		LoginInfo anotherloginInfo = new LoginInfo(anyLogin, "anotherPass", USER);
		anotherloginInfo.hashPassword();
		
		when(repository.findByLogin(anyLogin)).thenReturn(Optional.of(anotherloginInfo));
		subject.login(loginInfo, callback);
		
		verify(callback).onError(loginInfo);
		verify(callback, never()).onSuccess(loginInfo);
		verify(tokenService, never()).createTokenFor(loginInfo);
	}
	
	@Test
	public void shouldFailLoginWhenUserDoesNotExists() {
		LoginInfo loginInfo = new LoginInfo(anyLogin, anyPassword, USER);
		
		when(repository.findByLogin(anyLogin)).thenReturn(Optional.empty());
		subject.login(loginInfo, callback);
		
		verify(callback).onError(loginInfo);
		verify(callback, never()).onSuccess(loginInfo);
		verify(tokenService, never()).createTokenFor(loginInfo);
	}

}
