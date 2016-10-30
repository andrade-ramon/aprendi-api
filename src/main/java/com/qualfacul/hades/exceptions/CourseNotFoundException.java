package com.qualfacul.hades.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = NOT_FOUND, reason = "Course not found")
public class CourseNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
