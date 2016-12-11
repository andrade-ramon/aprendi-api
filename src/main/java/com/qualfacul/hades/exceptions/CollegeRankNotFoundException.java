package com.qualfacul.hades.exceptions;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = NOT_FOUND, reason = "CollegeRank not found")
public class CollegeRankNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -2552400284805320353L;
}
