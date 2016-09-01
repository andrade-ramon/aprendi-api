package com.qualfacul.hades.conversation;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.college.College;
import com.qualfacul.hades.login.LoggedUserManager;
import com.qualfacul.hades.login.LoginInfo;

@WebComponent
public class ConversationStartDTOToConversationConverter implements Converter<ConversationStartDTO, Conversation>{

	@Autowired
	private LoggedUserManager loggedUserManager;
	
	@Autowired
	private ConversationService conversationService;
	
	@Override
	public Conversation convert(ConversationStartDTO source) {
		LoginInfo loginInfo = loggedUserManager.getLoginInfo();
		Calendar nowDate = Calendar.getInstance();
		
		Conversation conversation = new Conversation();
		conversation.setCreatedAt(nowDate);
		conversation.setCollege(new College(source.getCollege()));
		List<ConversationMessage> messages = new ArrayList<>();

		ConversationMessage conversationMessage = new ConversationMessage(conversation, 
																			loginInfo.getId(),
																			source.getMessage(), 
																			conversationService.getUserDirection(loginInfo));
		conversationMessage.setSentAt(nowDate);
		messages.add(conversationMessage);
		
		conversation.setMessages(messages);
		return conversation;
	}

}
