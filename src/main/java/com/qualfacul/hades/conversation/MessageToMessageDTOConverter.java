package com.qualfacul.hades.conversation;

import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;

@WebComponent
public class MessageToMessageDTOConverter implements Converter<Message, MessageDTO>{

	@Override
	public MessageDTO convert(Message source) {
		MessageDTO MessageDTO = new MessageDTO();
		MessageDTO.setId(source.getId());
		MessageDTO.setConversationId(source.getConversation().getId());
		MessageDTO.setSentAt(source.getSentAt());
		MessageDTO.setMessage(source.getMessageContent().getText());;
		MessageDTO.setDirection(source.getDirection());
		return MessageDTO;
	}

}
