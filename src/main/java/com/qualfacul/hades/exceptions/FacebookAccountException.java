package com.qualfacul.hades.exceptions;

public class FacebookAccountException extends Exception{
	private static final long serialVersionUID = 1L;
	public FacebookAccountException(){
		super("A Facebook account is not allowed to use this function");
	}
}
