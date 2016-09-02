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

import org.hibernate.annotations.Where;

import com.qualfacul.hades.login.LoginInfo;

@Entity
@Where(clause = "deleted = 0")
@Table(name = "messages")
public class Message {

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
	@OneToOne(mappedBy = "message", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private MessageContent messageContent;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "direction", nullable = false)
	private ConversationDirection direction;

	@Column(name = "deleted")
	private boolean deleted;

	@Deprecated
	public Message() {
	}

	public Message(Conversation conversation, Calendar sentAt, String message, ConversationDirection direction) {
		this.conversation = conversation;
		this.sentAt = sentAt;
		this.messageContent = new MessageContent(this, message);
		this.direction = direction;
		this.deleted = false;
	}

	public Message(Conversation conversation, String message, ConversationDirection direction) {
		this.conversation = conversation;
		this.setCurrentDate();
		this.messageContent = new MessageContent(this, message);
		this.direction = direction;
		this.deleted = false;
	}

	public Message(Conversation conversation, MessageContent messageContent, ConversationDirection direction) {
		this.conversation = conversation;
		this.messageContent = messageContent;
		this.direction = direction;
		this.setCurrentDate();
	}

	public boolean isAuthor(LoginInfo loginInfo) {
		ConversationService conversationService = new ConversationService();
		ConversationDirection direction = conversationService.getUserDirection(loginInfo);
		boolean validOrigin = this.getDirection().equals(direction);
		boolean validUser = this.conversation.getUser().getId() == loginInfo.getId();
		return (validOrigin && validUser);
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

	public void setCurrentDate() {
		this.setSentAt(Calendar.getInstance());
	}

	public void setSentAt(Calendar sentAt) {
		this.sentAt = sentAt;
	}

	public MessageContent getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(MessageContent messageContent) {
		this.messageContent = messageContent;
	}

	public ConversationDirection getDirection() {
		return direction;
	}

	public void setDirection(ConversationDirection direction) {
		this.direction = direction;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
