package com.qualfacul.hades.crypt;

import org.glassfish.jersey.internal.util.Base64;

public class Base64Utils {

	public static String encode(String word) {
		return new String(Base64.encode(word.getBytes()));
	}
	
	public static String decode(String word) {
		return new String(Base64.decode(word.getBytes()));
	}

	
}
