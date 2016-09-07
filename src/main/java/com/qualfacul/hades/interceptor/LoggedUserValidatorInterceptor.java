package com.qualfacul.hades.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.qualfacul.hades.annotation.OnlyStudents;
import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.exceptions.OnlyStudentsCanAccessException;
import com.qualfacul.hades.login.LoggedUserManager;

@WebComponent
public class LoggedUserValidatorInterceptor extends HandlerInterceptorAdapter{
	
	private LoggedUserManager loggedUserManager;

	@Autowired
	public LoggedUserValidatorInterceptor(LoggedUserManager loggedUserManager) {
		this.loggedUserManager = loggedUserManager;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		
		if (handler instanceof HandlerMethod) {
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			boolean onlyStudentsCanPass = handlerMethod.getMethod().isAnnotationPresent(OnlyStudents.class);
			boolean isNotUser = !loggedUserManager.getLoginInfo().isUser();
			
			if (onlyStudentsCanPass && isNotUser) {
				throw new OnlyStudentsCanAccessException();
			}
		}
		return true;
	}
}
