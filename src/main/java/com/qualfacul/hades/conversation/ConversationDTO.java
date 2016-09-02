package com.qualfacul.hades.conversation;

import java.util.Calendar;
import java.util.List;

import com.qualfacul.hades.util.HadesDateFormat;

public class ConversationDTO {
	
	private Long id;
	private Long collegeId;
	private String createdAt;
	private List<MessageDTO> messages;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCollegeId() {
		return collegeId;
	}

	public void setCollegeId(Long collegeId) {
		this.collegeId = collegeId;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	private void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setCreatedAt(Calendar createdAt) {
		HadesDateFormat dateFormatter = new HadesDateFormat(HadesDateFormat.HADES_FULLDATE_FORMAT);
		this.setCreatedAt(dateFormatter.format(createdAt));
	}

	public List<MessageDTO> getMessages() {
		return messages;
	}

	public void setMessages(List<MessageDTO> messages) {
		this.messages = messages;
	}

}
