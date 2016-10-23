package com.qualfacul.hades.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.qualfacul.hades.annotation.OnlyAdmins;
import com.qualfacul.hades.annotation.OnlyColleges;
import com.qualfacul.hades.annotation.OnlyStudents;
import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.exceptions.OnlyAdminsCanAccessException;
import com.qualfacul.hades.exceptions.OnlyCollegesCanAccessException;
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
			boolean isNotUser = loggedUserManager.getLoginInfo() != null && !loggedUserManager.getLoginInfo().isUser();
			
			if (onlyStudentsCanPass && isNotUser) {
				throw new OnlyStudentsCanAccessException();
			}
			
			boolean onlyAdminsCanPass = handlerMethod.getMethod().isAnnotationPresent(OnlyAdmins.class);
			boolean isNotAdmin = loggedUserManager.getLoginInfo() != null && !loggedUserManager.getLoginInfo().isAdmin();
			
			if (onlyAdminsCanPass && isNotAdmin){
				throw new OnlyAdminsCanAccessException();
			}
			
			boolean onlyCollegesCanPass = handlerMethod.getMethod().isAnnotationPresent(OnlyColleges.class);
			boolean isNotCollege = loggedUserManager.getLoginInfo() != null && !loggedUserManager.getLoginInfo().isCollege();
			
			if (onlyCollegesCanPass && isNotCollege){
				throw new OnlyCollegesCanAccessException();
			}
		}
		return true;
	}
}
