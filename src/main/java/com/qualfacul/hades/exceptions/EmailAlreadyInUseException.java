package com.qualfacul.hades.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "E-mail address already in use")
public class EmailAlreadyInUseException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
