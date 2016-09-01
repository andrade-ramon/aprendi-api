package com.qualfacul.hades.conversation;

import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.qualfacul.hades.college.College;

@Entity
@Table(name = "conversation")
public class Conversation {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@ManyToOne
	private College college;
	
	@Column(name = "created_at", nullable = false)
	private Calendar createdAt;
	
	@NotNull
	@OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
	@Column(name = "messages", nullable = false)
	private List<ConversationMessage> messages;

	public Conversation(){
	}
	
	public Conversation(Long id){
		this.id = id;
	}
	
	public void setCreatedAtWithCurrentDate(){
		this.setCreatedAt(Calendar.getInstance());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public College getCollege() {
		return college;
	}

	public void setCollege(College college) {
		this.college = college;
	}

	public Calendar getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		this.createdAt = createdAt;
	}

	public List<ConversationMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<ConversationMessage> messages) {
		this.messages = messages;
	}
}
