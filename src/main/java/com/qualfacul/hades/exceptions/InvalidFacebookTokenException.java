package com.qualfacul.hades.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "hades.facebook.invalid.acessToken")
public class InvalidFacebookTokenException extends RuntimeException {
	private static final long serialVersionUID = 1L;

}
