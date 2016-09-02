package com.qualfacul.hades.conversation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.login.LoggedUserManager;
import com.qualfacul.hades.login.LoginInfo;

@WebComponent
public class ReplyMessageDTOToMessageConverter implements Converter<ReplyMessageDTO, Message>{

	@Autowired
	private LoggedUserManager loggedUserManager;
	
	@Autowired
	private ConversationService conversationService;
	
	@Override
	public Message convert(ReplyMessageDTO source) {
		Conversation conversation = new Conversation(source.getConversationId());
		LoginInfo loginInfo = loggedUserManager.getLoginInfo();
		Message message = new Message(conversation,
										source.getMessage(),
										conversationService.getUserDirection(loginInfo));
		return message;
	}

}
