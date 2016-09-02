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
import com.qualfacul.hades.user.User;
import com.qualfacul.hades.user.UserRepository;

@WebComponent
public class ConversationStartDTOToConversationConverter implements Converter<ConversationStartDTO, Conversation>{

	@Autowired
	private LoggedUserManager loggedUserManager;
	
	@Autowired
	private ConversationService conversationService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Conversation convert(ConversationStartDTO source) {
		LoginInfo loginInfo = loggedUserManager.getLoginInfo();
		Calendar nowDate = Calendar.getInstance();
		
		Conversation conversation = new Conversation();
		conversation.setDeleted(false);
		conversation.setCreatedAt(nowDate);
		conversation.setCollege(new College(source.getCollegeId()));
		User user = userRepository.findByEmail(loginInfo.getLogin()).get();
		conversation.setUser(user);
		List<Message> messages = new ArrayList<>();
		Message conversationMessage = new Message(conversation,
													nowDate,
													source.getMessage(),
													conversationService.getUserDirection(loginInfo));
		conversationMessage.setSentAt(nowDate);
		messages.add(conversationMessage);
		
		conversation.setMessages(messages);
		return conversation;
	}

}
