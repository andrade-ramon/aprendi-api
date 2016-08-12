package com.qualfacul.hades.exceptions;

public class UsernameNotFoundException extends Exception{
	private static final long serialVersionUID = 1L;
	public UsernameNotFoundException(){
		super("Your username was not found");
	}

}
