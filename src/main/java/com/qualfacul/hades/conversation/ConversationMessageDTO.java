package com.qualfacul.hades.conversation;

import java.util.Calendar;

import com.qualfacul.hades.util.HadesDateFormat;

public class ConversationMessageDTO {
	
	private Long id;
	
	private Long conversationId;
	
	private String sentAt;
	
	private Long senderId;
	
	private String message;
	
	private ConversationDirection direction;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getConversationId() {
		return conversationId;
	}
	public void setConversationId(Long conversationId) {
		this.conversationId = conversationId;
	}
	public String getSentAt() {
		return sentAt;
	}
	private void setSentAt(String sentAt) {
		this.sentAt = sentAt;
	}
	public void setSentAt(Calendar createdAt) {
		HadesDateFormat dateFormatter = new HadesDateFormat(HadesDateFormat.HADES_FULLDATE_FORMAT);
		this.setSentAt(dateFormatter.format(createdAt));
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
	public ConversationDirection getDirection() {
		return direction;
	}
	public void setDirection(ConversationDirection direction) {
		this.direction = direction;
	}
}
