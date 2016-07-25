package com.qualfacul.hades.exceptions;

//@ResponseStatus(value = HttpStatus.OK, reason = "This e-mail is already in use")
public class EmailAlreadyInUseException extends Exception {
	private static final long serialVersionUID = 1L;
	public EmailAlreadyInUseException(){
		super("This e-mail address is already in use");
	}
}
