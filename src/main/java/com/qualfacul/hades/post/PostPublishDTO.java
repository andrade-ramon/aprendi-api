package com.qualfacul.hades.post;

import javax.validation.constraints.NotNull;

public class PostPublishDTO {
	@NotNull(message = "hades.message.notreceived")
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
