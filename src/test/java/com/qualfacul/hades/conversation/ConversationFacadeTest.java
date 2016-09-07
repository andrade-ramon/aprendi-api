package com.qualfacul.hades.conversation;

import static com.qualfacul.hades.conversation.ConversationDirection.COLLEGE_TO_STUDENT;
import static com.qualfacul.hades.conversation.ConversationDirection.STUDENT_TO_COLLEGE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.qualfacul.hades.college.College;
import com.qualfacul.hades.college.CollegeRepository;
import com.qualfacul.hades.exceptions.CollegeNotFoundException;
import com.qualfacul.hades.exceptions.ConversationNotFoundException;
import com.qualfacul.hades.exceptions.MessageNotFoundException;
import com.qualfacul.hades.exceptions.UsernameNotFoundException;
import com.qualfacul.hades.login.LoggedUserManager;
import com.qualfacul.hades.login.LoginInfo;
import com.qualfacul.hades.login.LoginOrigin;
import com.qualfacul.hades.user.User;
import com.qualfacul.hades.user.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class ConversationFacadeTest {
	
	@Mock
	private LoggedUserManager loggedUserManager;
	@Mock
	private UserRepository userRepository;
	@Mock
	private CollegeRepository collegeRepository;
	@Mock
	private ConversationRepository conversationRepository;
	@Mock
	private MessageRepository messagesRepository;
	@Mock
	private ConversationSecurityAnalyser securityAnalyser;
	@Mock
	private User user;
	@Mock
	private College college;
	@Mock
	private LoginInfo loginInfo;
	
	private ConversationFacade subject;
	private Long collegeId;
	private String messageContent;
	private Long conversationId;
	private Long messageId;
	
	@Before
	public void setup() {
		subject = new ConversationFacade(loggedUserManager, userRepository, collegeRepository, conversationRepository, messagesRepository, securityAnalyser);
		collegeId = 123L;
		messageContent = "anyMessage";
		conversationId = 456L;
		messageId = 678L;
	}

	@Test
	public void shouldCreateAValidConversation() {
		when(loggedUserManager.getLoginInfo()).thenReturn(loginInfo);
		when(loginInfo.getLogin()).thenReturn("anyEmail");
		when(loginInfo.getLoginOrigin()).thenReturn(LoginOrigin.USER);
		when(userRepository.findByEmail("anyEmail")).thenReturn(Optional.of(user));
		when(collegeRepository.findById(collegeId)).thenReturn(Optional.of(college));
		when(college.getId()).thenReturn(collegeId);
		
		Conversation conversation = subject.createConversation(collegeId, messageContent);
		
		verify(conversationRepository).save(conversation);
		assertEquals(collegeId, conversation.getCollege().getId());
		assertEquals(messageContent, conversation.getMessages().get(0).getMessageContent().getText());
		assertEquals(STUDENT_TO_COLLEGE, conversation.getMessages().get(0).getDirection());
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void shouldNotCreateAConversationWhenUserIsNotFound() {
		when(loggedUserManager.getLoginInfo()).thenReturn(loginInfo);
		when(loginInfo.getLogin()).thenReturn("anyEmail");
		when(loginInfo.getLoginOrigin()).thenReturn(LoginOrigin.USER);
		when(userRepository.findByEmail("anyEmail")).thenReturn(Optional.empty());
		
		subject.createConversation(collegeId, messageContent);
	}
	
	@Test(expected = CollegeNotFoundException.class)
	public void shouldNotCreateAConversationWhenCollegeIsNotFound() {
		when(loggedUserManager.getLoginInfo()).thenReturn(loginInfo);
		when(loginInfo.getLogin()).thenReturn("anyEmail");
		when(loginInfo.getLoginOrigin()).thenReturn(LoginOrigin.USER);
		when(userRepository.findByEmail("anyEmail")).thenReturn(Optional.of(user));
		when(collegeRepository.findById(collegeId)).thenReturn(Optional.empty());
		
		subject.createConversation(collegeId, messageContent);
	}
	
	@Test
	public void shouldReplyAMessageToAConversation() {
		Conversation conversation = new Conversation(college, user);
		Message oldMessage = new Message(conversation, "oldMessage", STUDENT_TO_COLLEGE);
		conversation.addMessage(oldMessage);
		
		when(loggedUserManager.getLoginInfo()).thenReturn(loginInfo);
		when(loginInfo.getLoginOrigin()).thenReturn(LoginOrigin.COLLEGE);
		when(college.getId()).thenReturn(collegeId);
		when(conversationRepository.findById(conversationId)).thenReturn(Optional.of(conversation));
		
		Conversation conversationUpdated = subject.replyMessageFor(conversationId, messageContent);
		
		verify(messagesRepository).save(Mockito.any(Message.class));
		verify(securityAnalyser).validate(conversation);
		assertEquals(conversationUpdated.getMessages().size(), 2);
		assertEquals(messageContent, conversation.getMessages().get(1).getMessageContent().getText());
		assertEquals(COLLEGE_TO_STUDENT, conversation.getMessages().get(1).getDirection());
	}
	
	@Test(expected = ConversationNotFoundException.class)
	public void shouldNotReplyAMessageWhenConversationIsNotFound() {
		when(loggedUserManager.getLoginInfo()).thenReturn(loginInfo);
		when(loginInfo.getLoginOrigin()).thenReturn(LoginOrigin.COLLEGE);
		when(college.getId()).thenReturn(collegeId);
		when(conversationRepository.findById(conversationId)).thenReturn(Optional.empty());
		
		subject.replyMessageFor(conversationId, messageContent);
	}
	
	@Test
	public void shouldDeleteAMessage() {
		Conversation conversation = new Conversation(college, user);
		Message oldMessage = new Message(conversation, "oldMessage", STUDENT_TO_COLLEGE);
		conversation.addMessage(oldMessage);
		
		when(messagesRepository.findByIdAndConversationId(messageId, conversationId)).thenReturn(Optional.of(oldMessage));
		when(messagesRepository.save(oldMessage)).thenReturn(oldMessage);
		
		Long deleteMessageId = subject.deleteMessage(conversationId, messageId);
		
		verify(securityAnalyser).validate(oldMessage);
		assertTrue(oldMessage.isDeleted());
		assertEquals(oldMessage.getId(), deleteMessageId);
	}
	
	@Test(expected = MessageNotFoundException.class)
	public void shouldNotDeleteAMessageWhenMessageIsNotFound() {
		when(messagesRepository.findByIdAndConversationId(messageId, conversationId)).thenReturn(Optional.empty());		
		
		subject.deleteMessage(conversationId, messageId);
	}

}
