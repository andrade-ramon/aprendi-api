package com.qualfacul.hades.conversation;

import org.springframework.beans.factory.annotation.Autowired;

import com.qualfacul.hades.annotation.WebService;
import com.qualfacul.hades.college.College;
import com.qualfacul.hades.college.CollegeRepository;
import com.qualfacul.hades.exceptions.CollegeNotFoundException;
import com.qualfacul.hades.exceptions.ConversationNotFoundException;
import com.qualfacul.hades.exceptions.MessageNotFoundException;
import com.qualfacul.hades.exceptions.UsernameNotFoundException;
import com.qualfacul.hades.login.LoggedUserManager;
import com.qualfacul.hades.login.LoginInfo;
import com.qualfacul.hades.user.User;
import com.qualfacul.hades.user.UserRepository;

@WebService
public class ConversationFacade {

	private LoggedUserManager loggedUserManager;
	private UserRepository userRepository;
	private CollegeRepository collegeRepository;
	private ConversationRepository conversationRepository;
	private MessageRepository messagesRepository;
	private ConversationSecurityAnalyser securityAnalyser;

	@Autowired
	public ConversationFacade(LoggedUserManager loggedUserManager, UserRepository userRepository, CollegeRepository collegeRepository,
					ConversationRepository conversationRepository, MessageRepository messagesRepository,
					ConversationSecurityAnalyser securityAnalyser) {
		this.loggedUserManager = loggedUserManager;
		this.userRepository = userRepository;
		this.collegeRepository = collegeRepository;
		this.conversationRepository = conversationRepository;
		this.messagesRepository = messagesRepository;
		this.securityAnalyser = securityAnalyser;
	}

	public Conversation createConversation(Long collegeId, String messageContent) {
		LoginInfo loginInfo = loggedUserManager.getLoginInfo();

		User user = userRepository.findByEmail(loginInfo.getLogin()).orElseThrow(UsernameNotFoundException::new);
		College college = collegeRepository.findById(collegeId).orElseThrow(CollegeNotFoundException::new);

		Conversation conversation = new Conversation(college, user);

		Message message = new Message(conversation, messageContent, ConversationDirection.STUDENT_TO_COLLEGE);

		conversation.addMessage(message);
		conversationRepository.save(conversation);

		return conversation;
	}

	public Conversation replyMessageFor(Long conversationId, String text) {
		Conversation conversation = conversationRepository.findById(conversationId).orElseThrow(ConversationNotFoundException::new);

		securityAnalyser.validate(conversation);
		LoginInfo loginInfo = loggedUserManager.getLoginInfo();
		Message newMessage = new Message(conversation, text, loginInfo.getLoginOrigin().getConversationDirection());

		conversation.addMessage(newMessage);
		messagesRepository.save(newMessage);

		return conversation;
	}

	public Long deleteMessage(Long conversationId, Long messageId) {
		Message message = messagesRepository.findByIdAndConversationId(messageId, conversationId).orElseThrow(MessageNotFoundException::new);

		securityAnalyser.validate(message);

		message.markAsDeleted();
		return messagesRepository.save(message).getId();
	}
}
