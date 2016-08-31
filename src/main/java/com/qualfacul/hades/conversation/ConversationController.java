package com.qualfacul.hades.conversation;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.Post;
import com.qualfacul.hades.login.LoggedUserManager;
import com.qualfacul.hades.login.LoginInfo;

@RestController
public class ConversationController {
	
	@Autowired
	private LoggedUserManager loggedUserManager;
	
	@Autowired
	private ConversationDTOToConversationConverter conversationConverter;
	
	@Autowired
	private ConversationMessageDTOToConversationMessageConverter messageConverter;
	
	@Autowired
	private ConversationRepository conversationRepository;
	
	@Post("/conversation/new")
	public void newConversation(@RequestBody @NotBlank ConversationDTO conversationDTO){
		LoginInfo loginInfo = loggedUserManager.getLoginInfo();
		conversationDTO.setSenderId(loginInfo.getId());
		Conversation conversation = conversationConverter.convert(conversationDTO);
		
		ConversationMessageDTO conversationMessageDTO = new ConversationMessageDTO();
		conversationMessageDTO.setDefaultStudentToCollegeInfo(conversation, loginInfo.getId(), conversationDTO.getMessage());
		ConversationMessage conversationMessage = messageConverter.convert(conversationMessageDTO);
		//conversationMessageRepository.save(conversationMessage);
		
		List<ConversationMessage> messages = new ArrayList<>();
		messages.add(conversationMessage);
		conversation.setMessages(messages);
		conversationRepository.save(conversation);
	}
}
