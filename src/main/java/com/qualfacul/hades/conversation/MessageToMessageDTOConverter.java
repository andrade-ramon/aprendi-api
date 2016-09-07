package com.qualfacul.hades.conversation;

import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;

@WebComponent
public class MessageToMessageDTOConverter implements Converter<Message, MessageDTO>{

	@Override
	public MessageDTO convert(Message from) {
		MessageDTO MessageDTO = new MessageDTO();
		MessageDTO.setId(from.getId());
		MessageDTO.setSentAt(from.getSentAt());
		MessageDTO.setMessage(from.getMessageContent().getText());;
		MessageDTO.setDirection(from.getDirection());
		return MessageDTO;
	}

}
