package com.hades.interceptor;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hades.annotation.PermitEndpoint;
import com.hades.configuration.security.TokenAuthenticationService;

@Component
public class StatelessAuthenticationInterceptor extends HandlerInterceptorAdapter{

	@Autowired
	private TokenAuthenticationService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.addHeader("Access-Control-Allow-Headers", "Authorization");
		response.addHeader("Access-Control-Allow-Headers", "Content-Type");
		
		if (handler instanceof HandlerMethod) {		
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			boolean permitRequest = handlerMethod.getMethod().isAnnotationPresent(PermitEndpoint.class);
			
			if (permitRequest) {
				return true;
			}
	
			Authentication authentication = tokenService.getAuthentication(request);
			if (authentication == null) {
				response.setStatus(UNAUTHORIZED.value());
				return false;
			}
			SecurityContextHolder.getContext().setAuthentication(authentication);
		}
		
		return true;
	}
}
