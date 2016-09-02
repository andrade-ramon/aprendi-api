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

import org.hibernate.annotations.Where;

import com.qualfacul.hades.college.College;
import com.qualfacul.hades.user.User;

@Entity
@Where(clause = "deleted = 0")
@Table(name = "conversation")
public class Conversation {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@NotNull
	@ManyToOne
	private User user;
	
	@NotNull
	@ManyToOne
	private College college;
	
	@Column(name = "created_at", nullable = false)
	private Calendar createdAt;
	
	@NotNull
	@OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
	@Column(name = "messages", nullable = false)
	private List<Message> messages;
	
	@Column(name = "deleted", nullable = false)
	private boolean deleted;

	public Conversation(){
	}
	
	public Conversation(Long id){
		this.id = id;
		this.deleted = false;
	}
	
	public Message getAuthorMessage(){
		return this.getMessages().get(0);
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
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

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.getMessages().forEach(message -> message.setDeleted(true));
		this.deleted = deleted;
	}
}
