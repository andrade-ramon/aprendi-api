package com.qualfacul.hades.interceptor;

import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS;
import static org.springframework.http.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.hades.annotation.PublicEndpoint;
import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.configuration.security.TokenAuthenticationService;

@WebComponent
public class StatelessAuthenticationInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private TokenAuthenticationService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		prepareResponse(response);

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			boolean permitRequest = handlerMethod.getMethod().isAnnotationPresent(PublicEndpoint.class);
			boolean isErrorPath = "/error".equals(request.getServletPath());
			
			if (permitRequest | isErrorPath) {
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

	private void prepareResponse(HttpServletResponse response) {
		if (response.getHeader(ACCESS_CONTROL_ALLOW_ORIGIN) == null) {
			response.addHeader(ACCESS_CONTROL_ALLOW_ORIGIN, "*");
		}
		
		if (response.getHeader(ACCESS_CONTROL_ALLOW_HEADERS) == null) {
			response.addHeader(ACCESS_CONTROL_ALLOW_HEADERS, AUTHORIZATION);
			response.addHeader(ACCESS_CONTROL_ALLOW_HEADERS, CONTENT_TYPE);
		}
	}
}
