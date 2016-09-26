package com.qualfacul.hades.conversation;

import static com.qualfacul.hades.conversation.ConversationDirection.COLLEGE_TO_STUDENT;
import static com.qualfacul.hades.conversation.ConversationDirection.STUDENT_TO_COLLEGE;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.qualfacul.hades.college.College;
import com.qualfacul.hades.exceptions.UnauthorizedConversationSecurityException;
import com.qualfacul.hades.login.LoggedUserManager;
import com.qualfacul.hades.login.LoginInfo;
import com.qualfacul.hades.login.LoginOrigin;
import com.qualfacul.hades.user.User;

@RunWith(MockitoJUnitRunner.class)
public class ConversationSecurityAnalyserTest {
	
	@Mock
	private LoggedUserManager loggedUserManager;
	
	private ConversationSecurityAnalyser subject;
	@Mock
	private College college;
	@Mock
	private User user;
	
	private LoginInfo loginInfo;
	private LoginInfo otherLoginInfo;
	
	@Before
	public void setup() {
		loginInfo = new LoginInfo("anyLogin", "anyPass", LoginOrigin.USER);
		otherLoginInfo = new LoginInfo("otherLogin", "otherPass", LoginOrigin.COLLEGE);
		
		subject = new ConversationSecurityAnalyser(loggedUserManager);
		when(loggedUserManager.getLoginInfo()).thenReturn(loginInfo);
	}

	@Test
	public void shouldValidateAValidConversation() {
		Conversation conversation = new Conversation(college, user);
		Message message = new Message(conversation, "anyMessage", STUDENT_TO_COLLEGE);
		conversation.addMessage(message);
		
		when(user.getLoginInfo()).thenReturn(loginInfo);
		
		subject.validate(conversation);
	}
	
	@Test(expected = UnauthorizedConversationSecurityException.class)
	public void shouldValidateAInvalidConversation() {
		Conversation conversation = new Conversation(college, user);
		Message message = new Message(conversation, "anyMessage", COLLEGE_TO_STUDENT);
		conversation.addMessage(message);
		
		when(user.getLoginInfo()).thenReturn(otherLoginInfo);
		
		subject.validate(conversation);
	}
	
	@Test
	public void shouldValidateAValidMessage() {
		Conversation conversation = new Conversation(college, user);
		Message message = new Message(conversation, "anyMessage", STUDENT_TO_COLLEGE);
		conversation.addMessage(message);
		
		when(user.getLoginInfo()).thenReturn(loginInfo);
		
		subject.validate(message);
	}
	
	@Test(expected = UnauthorizedConversationSecurityException.class)
	public void shouldValidateAInvalidMessage() {
		Conversation conversation = new Conversation(college, user);
		Message message = new Message(conversation, "anyMessage", STUDENT_TO_COLLEGE);
		conversation.addMessage(message);
		
		when(user.getLoginInfo()).thenReturn(otherLoginInfo);
		
		subject.validate(message);
	}

}
