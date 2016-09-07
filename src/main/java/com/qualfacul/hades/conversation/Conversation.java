package com.qualfacul.hades.conversation;

import java.util.ArrayList;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Where;

import com.qualfacul.hades.college.College;
import com.qualfacul.hades.user.User;

@Entity
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
	
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at")
	private Calendar createdAt = Calendar.getInstance();
		
	@OneToMany(mappedBy = "conversation", cascade = CascadeType.ALL)
	@Where(clause = "deleted = 0")
	private List<Message> messages = new ArrayList<>();

	@Deprecated //Hibernate eyes only
	public Conversation() {
	}

	public Conversation(College college, User user) {
		this.college = college;
		this.user = user;
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

	public void addMessage(Message message) {
		this.messages.add(message);
	}

}
