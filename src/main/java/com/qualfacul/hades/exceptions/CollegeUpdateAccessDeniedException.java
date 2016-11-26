package com.qualfacul.hades.exceptions;

import static org.springframework.http.HttpStatus.FORBIDDEN;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = FORBIDDEN, reason = "You are not allowed to update this college")
public class CollegeUpdateAccessDeniedException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}