package com.qualfacul.hades.conversation;

import static org.springframework.http.HttpStatus.CREATED;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.Delete;
import com.qualfacul.hades.annotation.Get;
import com.qualfacul.hades.annotation.OnlyStudents;
import com.qualfacul.hades.annotation.Patch;
import com.qualfacul.hades.annotation.Post;
import com.qualfacul.hades.annotation.PublicEndpoint;
import com.qualfacul.hades.exceptions.ConversationNotFoundException;
import com.qualfacul.hades.exceptions.MessageNotFoundException;

@RestController
@RequestMapping("/colleges/{collegeId}")
public class ConversationController {
	
	@Autowired
	private ConversationRepository conversationRepository;
	@Autowired
	private MessageRepository messagesRepository;
	@Autowired
	private ConversationToDTOConverter conversationConverter;
	@Autowired
	private MessageToDTOConverter messageConverter;
	@Autowired
	private ConversationFacade facade;
	
	@PublicEndpoint
	@Get("/conversations")
	public List<ConversationDTO> listAll(@PathVariable final Long collegeId){
		return conversationRepository.findAllByCollegeId(collegeId)
									 .stream()
									 .map(conversation -> conversationConverter
										.fromConversation(conversation)
										.withMessages()
										.convert())
									 .collect(Collectors.toList());
	}
	
	@OnlyStudents
	@Post(value = "/conversations", responseStatus = CREATED)
	public ConversationDTO start(@PathVariable final Long collegeId, @RequestBody String messageText) {
		Conversation conversation = facade.createConversation(collegeId, messageText);
		return conversationConverter.fromConversation(conversation)
									.withMessages()
									.convert();
	}
	
	@PublicEndpoint
	@Get("/conversations/{conversationId}")
	public ConversationDTO find(@PathVariable final Long conversationId){
		return conversationRepository.findById(conversationId)
									 .map(conversation -> conversationConverter
										.fromConversation(conversation)
										.withMessages()
										.convert())
				.orElseThrow(ConversationNotFoundException::new);
	}
	
	@Patch(value = "/conversations/{conversationId}", responseStatus = CREATED)
	public ConversationDTO reply(@PathVariable Long conversationId, @RequestBody String messageText){
		Conversation conversation = facade.replyMessageFor(conversationId, messageText);
		return conversationConverter.fromConversation(conversation)
									.withMessages()
									.convert();
	}
	
	@PublicEndpoint
	@Get("/conversations/{conversationId}/messages/{messageId}")
	public MessageDTO findMessage(@PathVariable Long conversationId, @PathVariable Long messageId){
		return messagesRepository.findByIdAndConversationId(messageId, conversationId)
				.map(conversationMessage -> messageConverter.convert(conversationMessage))
				.orElseThrow(MessageNotFoundException::new);
	}
	
	@Delete(value = "/conversations/{conversationId}/messages/{messageId}")
	public Long deleteMessage(@PathVariable final Long conversationId, @PathVariable final Long messageId){
		return facade.deleteMessage(conversationId, messageId);
	}
}
