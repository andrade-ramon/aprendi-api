package com.qualfacul.hades.conversation;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.qualfacul.hades.serialization.LocalDateTimeSerializer;


public class MessageDTO {
	
	private Long id;
	
	@JsonSerialize(using = LocalDateTimeSerializer.class)
	private LocalDateTime sentAt;
	
	private String content;
	
	private ConversationDirection direction;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	
	public LocalDateTime getSentAt() {
		return sentAt;
	}
	public void setSentAt(LocalDateTime createdAt) {
		this.sentAt = createdAt;
	}
	
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public ConversationDirection getDirection() {
		return direction;
	}
	public void setDirection(ConversationDirection direction) {
		this.direction = direction;
	}
}
