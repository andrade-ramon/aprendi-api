package com.qualfacul.hades.exceptions;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = BAD_REQUEST, reason = "This college already have a login")
public class CollegeAlreadyHaveLoginException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}