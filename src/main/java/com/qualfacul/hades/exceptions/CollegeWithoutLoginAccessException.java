package com.qualfacul.hades.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = NOT_FOUND, reason = "This college doesn't have a login to be deleted")
public class CollegeWithoutLoginAccessException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}