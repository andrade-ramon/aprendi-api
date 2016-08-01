package com.qualfacul.hades.exceptions;

public class EmailAlreadyInUseException extends Exception {
	private static final long serialVersionUID = 1L;
	public EmailAlreadyInUseException(){
		super("This e-mail address is already in use");
	}
}
