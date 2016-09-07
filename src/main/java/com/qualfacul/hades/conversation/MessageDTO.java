package com.qualfacul.hades.conversation;

import java.util.Calendar;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qualfacul.hades.serialization.FullDateCalendarSerializer;


public class MessageDTO {
	
	private Long id;
	
	@JsonSerialize(using = FullDateCalendarSerializer.class)
	private Calendar sentAt;
	
	private String message;
	
	private ConversationDirection direction;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public Calendar getSentAt() {
		return sentAt;
	}
	public void setSentAt(Calendar createdAt) {
		this.sentAt = createdAt;
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
