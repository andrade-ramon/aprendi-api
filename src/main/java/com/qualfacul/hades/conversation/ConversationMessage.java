package com.qualfacul.hades.conversation;

import java.util.Calendar;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "conversation_messages")
public class ConversationMessage {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@OneToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	private Conversation conversation;
	
	@NotNull
	@Column(name = "sent_at", nullable = false)
	private Calendar sentAt;
	
	@NotNull
	@Column(name = "sender_id", nullable = false)
	private Long senderId;
	
	@NotNull
	@Column(name = "message", nullable = false)
	private String message;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "direction", nullable = false)
	private ConversationDirection direction;

	@Deprecated
	public ConversationMessage(){
	}
	
	public ConversationMessage(Conversation conversation, Long senderId, String message, ConversationDirection direction){
		this.conversation = conversation;
		this.senderId = senderId;
		this.message = message;
		this.direction = direction;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Conversation getConversation() {
		return conversation;
	}
	public void setConversation(Conversation conversation) {
		this.conversation = conversation;
	}
	public Calendar getSentAt() {
		return sentAt;
	}
	public void setSentAt(Calendar sentAt) {
		this.sentAt = sentAt;
	}
	public void setSentAtWithCurrentDate(){
		this.setSentAt(Calendar.getInstance());
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
