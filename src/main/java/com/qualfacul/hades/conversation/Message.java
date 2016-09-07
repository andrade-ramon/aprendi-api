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
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.Where;

@Entity
@Where(clause = "deleted = 0")
@Table(name = "message")
public class Message {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@OneToOne(cascade = CascadeType.MERGE)
	private Conversation conversation;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "sent_at")
	private Calendar sentAt;

	@PrimaryKeyJoinColumn
	@OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private MessageContent messageContent;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "direction", nullable = false)
	private ConversationDirection direction;

	@Column(name = "deleted")
	@Type(type = "org.hibernate.type.NumericBooleanType")
	private Boolean deleted;

	@Deprecated //Hibernate eyes only
	public Message() {
	}

	public Message(Conversation conversation, String message, ConversationDirection direction) {
		this.conversation = conversation;
		this.messageContent = new MessageContent(this, message);
		this.direction = direction;
		this.deleted = false;
		this.sentAt = Calendar.getInstance();
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

	public void markAsDeleted() {
		this.deleted = true;
	}
}
