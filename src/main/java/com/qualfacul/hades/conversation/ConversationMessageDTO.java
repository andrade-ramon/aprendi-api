package com.qualfacul.hades.conversation;

import java.util.Calendar;

public class ConversationMessageDTO {
	
	private Long id;
	
	private Conversation conversation;
	
	private Calendar sentAt;
	
	private Long senderId;
	
	private String message;
	
	private ConversationDirection direction;

	public ConversationMessageDTO(){
	}
	
	private void setDefaultInfo(Conversation conversation, Long senderId, String message){
		Calendar sentAt = Calendar.getInstance();
		setConversation(conversation);
		setSenderId(senderId);
		setMessage(message);
		setSentAt(sentAt);
	}
	
	public void setDefaultStudentToCollegeInfo(Conversation conversation, Long senderId, String message){
		setDefaultInfo(conversation, senderId, message);
		setDirection(ConversationDirection.STUDENT_TO_COLLEGE);
	}
	
	public void setDefaultCollegeToStudentInfo(Conversation conversation, Long senderId, String message){
		setDefaultInfo(conversation, senderId, message);
		setDirection(ConversationDirection.COLLEGE_TO_STUDENT);
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
