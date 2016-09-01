package com.qualfacul.hades.conversation;

import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;

@WebComponent
public class ConversationMessageToConversationMessageDTOConverter implements Converter<ConversationMessage, ConversationMessageDTO>{

	@Override
	public ConversationMessageDTO convert(ConversationMessage source) {
		ConversationMessageDTO conversationMessageDTO = new ConversationMessageDTO();
		conversationMessageDTO.setId(source.getId());
		conversationMessageDTO.setConversationId(source.getConversation().getId());
		conversationMessageDTO.setSentAt(source.getSentAt());
		conversationMessageDTO.setSenderId(source.getSenderId());
		conversationMessageDTO.setMessage(source.getMessage());
		conversationMessageDTO.setDirection(source.getDirection());
		return conversationMessageDTO;
	}

}
