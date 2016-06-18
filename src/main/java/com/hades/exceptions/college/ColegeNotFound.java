package com.hades.exceptions.college;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "College not found")
public class ColegeNotFound extends RuntimeException{
	private static final long serialVersionUID = 1L;
}
