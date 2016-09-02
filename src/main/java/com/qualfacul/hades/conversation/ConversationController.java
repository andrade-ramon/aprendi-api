package com.qualfacul.hades.conversation;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.Delete;
import com.qualfacul.hades.annotation.Get;
import com.qualfacul.hades.annotation.Post;
import com.qualfacul.hades.exceptions.MessageNotFoundException;
import com.qualfacul.hades.exceptions.ConversationNotFoundException;
import com.qualfacul.hades.exceptions.UsernameNotAllowedException;
import com.qualfacul.hades.login.LoggedUserManager;
import com.qualfacul.hades.login.LoginInfo;

@RestController
public class ConversationController {
	
	@Autowired
	private ConversationRepository conversationRepository;

	@Autowired
	private ConversationMessageRepository messageRepository;
	
	@Autowired
	private ConversationStartDTOToConversationConverter conversationStartDTOConverter;
	
	@Autowired
	private ConversationToConversationDTOConverter conversationConverter;
	
	@Autowired
	private MessageToMessageDTOConverter messageConverter;
	
	@Autowired
	private ReplyMessageDTOToMessageConverter replyMessageConverter;
	
	@Autowired
	private LoggedUserManager loggedUserManager;
	
	@Get(value = "/conversations/{id}")
	public ConversationDTO getConversation(@PathVariable long id){
		return conversationRepository.findById(id)
				.map(conversation -> conversationConverter
										.fromConversation(conversation)
										.withMessages()
										.convert())
				.orElseThrow(ConversationNotFoundException::new);
	}
	
	@Get(value = "/conversations/{conversationId}/messages/{id}")
	public MessageDTO getMessage(@PathVariable long conversationId, @PathVariable Long id){
		return messageRepository.findByIdAndConversationId(id, conversationId)
				.map(conversationMessage -> messageConverter.convert(conversationMessage))
				.orElseThrow(MessageNotFoundException::new);
	}
	
	@Post(value = "/conversations", responseStatus = CREATED)
	public ConversationDTO newConversation(@RequestBody @Valid ConversationStartDTO conversationStartDTO){
		Conversation conversation = conversationStartDTOConverter.convert(conversationStartDTO);
		conversationRepository.save(conversation);
		return conversationConverter.fromConversation(conversation).convert();
	}
	
	@Post(value = "/conversations/reply", responseStatus = CREATED)
	public MessageDTO newReply(@RequestBody @Valid ReplyMessageDTO replyMessageDTO){
		Message conversationMessage = replyMessageConverter.convert(replyMessageDTO);
		messageRepository.save(conversationMessage);
		conversationMessage.getMessageContent();
		return messageConverter.convert(conversationMessage);
	}
	
	@Delete(value = "/conversations/{id}")
	public void deleteConversation(@PathVariable Long id){
		Optional<Conversation> optionalConversation = conversationRepository.findById(id);
		if (!optionalConversation.isPresent()){
			throw new ConversationNotFoundException();
		}
		Conversation conversation = optionalConversation.get();
		LoginInfo loginInfo = loggedUserManager.getLoginInfo();
		Message firstMessage = conversation.getAuthorMessage();
		if (!firstMessage.isAuthor(loginInfo)){
			throw new UsernameNotAllowedException();
		}
		conversation.setDeleted(true);
		conversationRepository.save(conversation);
	}
	
	@Delete(value = "conversations/{conversationId}/messages/{id}")
	public void deleteMessage(@PathVariable Long conversationId, @PathVariable Long id){
		Optional<Message> optionalMessage = messageRepository.findByIdAndConversationId(id, conversationId);
		if (!optionalMessage.isPresent()){
			throw new MessageNotFoundException();
		}
		Message message = optionalMessage.get();
		LoginInfo loginInfo = loggedUserManager.getLoginInfo();
		if (!message.isAuthor(loginInfo)){
			throw new UsernameNotAllowedException();
		}
		message.setDeleted(true);
		messageRepository.save(message);
	}
}
