package com.qualfacul.hades.conversation;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "message_content")
public class MessageContent {

	@Id
	@GeneratedValue
	private Long id;

	@NotNull
	@OneToOne(cascade = CascadeType.MERGE)
	private Message message;

	@NotNull
	@Column(name = "text", nullable = false)
	private String text;

	@Deprecated
	public MessageContent() {
	}

	public MessageContent(Message message, String text) {
		this.message = message;
		this.text = text;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Message getMessage() {
		return message;
	}

	public void setMessage(Message message) {
		this.message = message;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
