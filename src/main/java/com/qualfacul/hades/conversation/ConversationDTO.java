package com.qualfacul.hades.conversation;

import org.hibernate.validator.constraints.NotEmpty;

public class ConversationDTO {
	@NotEmpty(message = "hades.college.not.received")
	private Long college;
	private Long senderId;
	@NotEmpty(message = "hades.message.not.received")
	private String message;
	
	public ConversationDTO(){
	}
	
	public ConversationDTO(Long college, Long senderId, String message) {
		this.college = college;
		this.senderId = senderId;
		this.message = message;
	}
	
	public Long getCollege() {
		return college;
	}
	public void setCollege(Long college) {
		this.college = college;
	}
	public Long getSenderId() {
		return senderId;
	}

	public void setSenderId(Long senderId) {
		this.senderId = senderId;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
