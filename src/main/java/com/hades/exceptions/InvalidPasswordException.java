package com.hades.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid credentials")
public class InvalidPasswordException extends LoginFailureException{
	private static final long serialVersionUID = 1L;
}
