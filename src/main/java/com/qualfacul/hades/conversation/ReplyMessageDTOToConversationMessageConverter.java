package com.qualfacul.hades.conversation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.login.LoggedUserManager;
import com.qualfacul.hades.login.LoginInfo;

@WebComponent
public class ReplyMessageDTOToConversationMessageConverter implements Converter<ReplyMessageDTO, ConversationMessage>{

	@Autowired
	private LoggedUserManager loggedUserManager;
	
	@Autowired
	private ConversationService conversationService;
	
	@Override
	public ConversationMessage convert(ReplyMessageDTO source) {
		Conversation conversation = new Conversation(source.getConversationId());
		LoginInfo loginInfo = loggedUserManager.getLoginInfo();
		conversation.setCreatedAtWithCurrentDate();
		ConversationMessage conversationMessage = new ConversationMessage(conversation, 
																			loginInfo.getId(), 
																			source.getMessage(), 
																			conversationService.getUserDirection(loginInfo));
		conversationMessage.setSentAt(conversation.getCreatedAt());
		return conversationMessage;
	}

}
