package com.qualfacul.hades.conversation;

import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;

@WebComponent
public class MessageToDTOConverter implements Converter<Message, MessageDTO> {

	@Override
	public MessageDTO convert(Message from) {
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.setId(from.getId());
		messageDTO.setSentAt(from.getSentAt());
		messageDTO.setContent(from.getMessageContent().getText());;
		messageDTO.setDirection(from.getDirection());
		return messageDTO;
	}

}
