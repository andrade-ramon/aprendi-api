package com.qualfacul.hades.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "hades.user.notfound")
public class UsernameNotFoundException extends RuntimeException{
	private static final long serialVersionUID = 1L;
}
