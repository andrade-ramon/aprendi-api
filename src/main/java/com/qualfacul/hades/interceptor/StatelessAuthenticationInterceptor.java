package com.qualfacul.hades.interceptor;

import static org.apache.commons.lang3.StringUtils.isEmpty;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.qualfacul.hades.annotation.PublicEndpoint;
import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.configuration.security.TokenAuthenticationService;
import com.qualfacul.hades.login.LoginAuthentication;
import com.qualfacul.hades.login.LoginInfo;

@WebComponent
public class StatelessAuthenticationInterceptor extends HandlerInterceptorAdapter {

	private TokenAuthenticationService tokenService;
	
	@Autowired
	public StatelessAuthenticationInterceptor(TokenAuthenticationService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		String tokenAuthenticated = null;
		
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			boolean permitRequest = handlerMethod.getMethod().isAnnotationPresent(PublicEndpoint.class);
			boolean isErrorPath = StringUtils.equals("/error", request.getServletPath());
			
			if (permitRequest | isErrorPath) {
				return true;
			}
			
			String jwtToken = request.getHeader(AUTHORIZATION);
			Optional<LoginInfo> optionalLoginInfo = tokenService.getUserFromToken(jwtToken);
				
			if (!optionalLoginInfo.isPresent()) {
				response.setStatus(UNAUTHORIZED.value());
				return false;
			}
			LoginInfo loginInfo = optionalLoginInfo.get();

			tokenService.createTokenFor(loginInfo);
			tokenAuthenticated = loginInfo.getToken();

			SecurityContextHolder.getContext().setAuthentication(new LoginAuthentication(loginInfo));
		}
		
		prepareResponseHeader(response, tokenAuthenticated);
		return true;
	}

	private void prepareResponseHeader(HttpServletResponse response, String tokenAuthenticated) {
		if (response.getHeader(ACCESS_CONTROL_ALLOW_METHODS) == null) {
			response.addHeader(ACCESS_CONTROL_ALLOW_METHODS , "GET, POST, PUT, PATCH, OPTIONS, DELETE");
		}
		
		if (response.getHeader(ACCESS_CONTROL_ALLOW_ORIGIN) == null) {
			response.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		}
		
		if (response.getHeader(ACCESS_CONTROL_ALLOW_HEADERS) == null) {
			response.addHeader(ACCESS_CONTROL_ALLOW_HEADERS, AUTHORIZATION);
			response.addHeader(ACCESS_CONTROL_ALLOW_HEADERS, CONTENT_TYPE);
		}
		
		if (!isEmpty(tokenAuthenticated)) {
			response.setHeader(ACCESS_CONTROL_EXPOSE_HEADERS, AUTHORIZATION);
			response.setHeader(AUTHORIZATION, tokenAuthenticated);
		}
	}
}
