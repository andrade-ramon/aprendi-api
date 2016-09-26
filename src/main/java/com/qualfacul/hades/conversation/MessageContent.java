package com.qualfacul.hades.conversation;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name = "message_content")
public class MessageContent {

	@Id
	@GeneratedValue(generator = "SharedPrimaryKeyGenerator")
	@GenericGenerator(
		name="SharedPrimaryKeyGenerator",
		strategy="foreign",
		parameters = @Parameter(name="property", value="message")
	)
	@Column(name = "message_id", unique = true, nullable = false)
	private Long messageId;
	
	@OneToOne
	@PrimaryKeyJoinColumn
	private Message message;
	
	@NotNull
	@Column(name = "text", nullable = false)
	private String text;

	@Deprecated //Hibernate eyes only
	public MessageContent() {
	}

	public MessageContent(Message message, String text) {
		this.message = message;
		this.messageId = message.getId();
		this.text = text;
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
