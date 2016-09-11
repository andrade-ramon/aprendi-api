package com.qualfacul.hades.conversation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.converter.ListConverter;

@WebComponent
public class ConversationToDTOConverter{
	
	@Autowired
	private MessageToDTOConverter messageConverter;
	
	public Builder fromConversation(Conversation conversation) {
		return new Builder(conversation);
	}
	
	public class Builder {
		
		private ConversationDTO dto;
		private Conversation conversation;
		
		public Builder(Conversation conversation) {
			this.conversation = conversation;
			dto = new ConversationDTO();
			dto.setId(conversation.getId());
			dto.setCreatedAt(conversation.getCreatedAt());
			dto.setCollegeId(conversation.getCollege().getId());
			dto.setStudentId(conversation.getUser().getId());
			dto.setStudentName(conversation.getUser().getName());
		}
		
		public Builder withMessages() {
			ListConverter<Message, MessageDTO> listConverter = new ListConverter<>(messageConverter);
			List<MessageDTO> messages = listConverter.convert(conversation.getMessages());
			dto.setMessages(messages);
			return this;
		}
		
		public ConversationDTO convert() {
			return this.dto;
		}
	}

}
