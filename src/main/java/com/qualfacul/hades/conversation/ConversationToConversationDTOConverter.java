package com.qualfacul.hades.conversation;

import java.util.List;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.converter.ListConverter;

@WebComponent
public class ConversationToConversationDTOConverter{
	
	public Builder fromConversation(Conversation conversation) {
		return new Builder(conversation);
	}
	
	public class Builder {
		
		private ConversationDTO conversationDTO;
		private Conversation conversation;
		
		public Builder(Conversation conversation) {
			this.conversation = conversation;
			this.conversationDTO = new ConversationDTO();
			conversationDTO.setId(conversation.getId());
			conversationDTO.setCreatedAt(conversation.getCreatedAt());
			conversationDTO.setCollegeId(conversation.getCollege().getId());
		}
		
		public Builder withMessages() {
			conversation.getMessages();
			ConversationMessageToConversationMessageDTOConverter messageConverter;
			messageConverter = new ConversationMessageToConversationMessageDTOConverter();
			ListConverter<ConversationMessage, ConversationMessageDTO> listConverter = new ListConverter<>(messageConverter);
			List<ConversationMessageDTO> messages = listConverter.convert(conversation.getMessages());
			conversationDTO.setMessages(messages);
			return this;
		}
		
		public ConversationDTO convert() {
			return this.conversationDTO;
		}
	}

}
