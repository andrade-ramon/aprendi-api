package com.hades.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import com.hades.configuration.security.TokenAuthenticationService;

public class StatelessAuthenticationFilter extends GenericFilterBean{
	
	private final TokenAuthenticationService tokenService;
	
	public StatelessAuthenticationFilter(TokenAuthenticationService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest =  (HttpServletRequest) request;

		Authentication authentication = tokenService.getAuthentication(httpRequest);
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		chain.doFilter(request, response);
		
		SecurityContextHolder.getContext().setAuthentication(null);
	}
	

}
