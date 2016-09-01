package com.qualfacul.hades.conversation;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class ConversationStartDTO {
	@NotNull(message = "hades.college.not.received")
	private Long college;
	@NotEmpty(message = "hades.message.not.received")
	private String message;
	
	public ConversationStartDTO(){
	}

	public Long getCollege() {
		return college;
	}

	public void setCollege(Long college) {
		this.college = college;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
