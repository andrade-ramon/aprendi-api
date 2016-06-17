package com.qualfacul.hades.interceptor;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.qualfacul.hades.annotation.InternalEndpoint;
import com.qualfacul.hades.annotation.PermitEndpoint;
import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.configuration.security.TokenAuthenticationService;

@WebComponent
public class StatelessAuthenticationInterceptor extends HandlerInterceptorAdapter {

	@Value("${internal.secret}")
	private String internalAuth;

	@Autowired
	private TokenAuthenticationService tokenService;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

		prepareResponse(response);

		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			boolean permitRequest = handlerMethod.getMethod().isAnnotationPresent(PermitEndpoint.class);
			boolean isErrorPath = "/error".equals(request.getServletPath());
			boolean isInternal = internalAuth.equals(request.getHeader(AUTHORIZATION)) 
							&& handlerMethod.getMethod().isAnnotationPresent(InternalEndpoint.class);

			if (permitRequest | isErrorPath | isInternal) {
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
		if (response.getHeader("Access-Control-Allow-Origin") == null) {
			response.addHeader("Access-Control-Allow-Origin", "*");
		}

		if (response.getHeader("Access-Control-Allow-Headers") == null) {
			response.addHeader("Access-Control-Allow-Headers", AUTHORIZATION);
			response.addHeader("Access-Control-Allow-Headers", CONTENT_TYPE);
		}
	}
}
