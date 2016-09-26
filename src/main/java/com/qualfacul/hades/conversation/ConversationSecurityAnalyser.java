package com.qualfacul.hades.conversation;

import org.springframework.beans.factory.annotation.Autowired;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.exceptions.UnauthorizedConversationSecurityException;
import com.qualfacul.hades.login.LoggedUserManager;
import com.qualfacul.hades.login.LoginInfo;

@WebComponent
public class ConversationSecurityAnalyser {
	
	private LoggedUserManager loggedUserManager;
	
	@Autowired
	public ConversationSecurityAnalyser(LoggedUserManager loggedUserManager) {
		this.loggedUserManager = loggedUserManager;
	}

	public void validate(Message message) {
		LoginInfo loginInfo = loggedUserManager.getLoginInfo();
		
		boolean validDirection = loginInfo.getLoginOrigin().getConversationDirection() == message.getDirection();
		boolean validUser = message.getConversation().getUser().getLoginInfo().equals(loginInfo);
		
		if (validDirection && validUser) {
			return;
		}
		throw new UnauthorizedConversationSecurityException();
	}
	

	public void validate(Conversation conversation) {
		LoginInfo loginInfo = loggedUserManager.getLoginInfo();
		boolean validUser = conversation.getUser().getLoginInfo().equals(loginInfo);
		
		if (validUser) {
			return;
		}
		throw new UnauthorizedConversationSecurityException();
	}
	
}
