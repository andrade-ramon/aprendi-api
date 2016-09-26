package com.qualfacul.hades.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Not allowed to do this operation with this conversation")
public class UnauthorizedConversationSecurityException extends RuntimeException {
	private static final long serialVersionUID = 1L;
}
