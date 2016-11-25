package com.qualfacul.hades.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "You are not allowed to perform this action")
public class AccessDeniedException extends RuntimeException{
	private static final long serialVersionUID = 1L;
}
