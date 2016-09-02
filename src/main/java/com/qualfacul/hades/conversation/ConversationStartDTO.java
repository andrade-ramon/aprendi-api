package com.qualfacul.hades.conversation;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class ConversationStartDTO {
	
	@NotNull(message = "hades.college.not.received")
	private Long collegeId;
	@NotEmpty(message = "hades.message.not.received")
	private String message;
	
	public ConversationStartDTO(){
	}

	public Long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Long collegeId) {
		this.collegeId = collegeId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
