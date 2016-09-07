package com.qualfacul.hades.conversation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.converter.ListConverter;

@WebComponent
public class ConversationToConversationDTOConverter{
	
	@Autowired
	private MessageToMessageDTOConverter messageConverter;
	
	public Builder fromConversation(Conversation conversation) {
		return new Builder(conversation);
	}
	
	public class Builder {
		
		private ConversationDTO conversationDTO;
		private Conversation conversation;
		
		public Builder(Conversation conversation) {
			this.conversation = conversation;
			conversationDTO = new ConversationDTO();
			conversationDTO.setId(conversation.getId());
			conversationDTO.setCreatedAt(conversation.getCreatedAt());
			conversationDTO.setCollegeId(conversation.getCollege().getId());
		}
		
		public Builder withMessages() {
			ListConverter<Message, MessageDTO> listConverter = new ListConverter<>(messageConverter);
			List<MessageDTO> messages = listConverter.convert(conversation.getMessages());
			conversationDTO.setMessages(messages);
			return this;
		}
		
		public ConversationDTO convert() {
			return this.conversationDTO;
		}
	}

}
