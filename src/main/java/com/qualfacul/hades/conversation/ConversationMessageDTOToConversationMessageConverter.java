package com.qualfacul.hades.conversation;

import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;

@WebComponent
public class ConversationMessageDTOToConversationMessageConverter implements Converter<ConversationMessageDTO, ConversationMessage>{

	@Override
	public ConversationMessage convert(ConversationMessageDTO source) {
		ConversationMessage conversationMessage = new ConversationMessage();
		conversationMessage.setConversation(source.getConversation());
		conversationMessage.setSenderId(source.getSenderId());
		conversationMessage.setSentAt(source.getSentAt());
		conversationMessage.setMessage(source.getMessage());
		conversationMessage.setDirection(source.getDirection());
		return conversationMessage;
	}

}
