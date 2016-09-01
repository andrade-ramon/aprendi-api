package com.qualfacul.hades.conversation;

import static org.springframework.http.HttpStatus.CREATED;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.Get;
import com.qualfacul.hades.annotation.Post;
import com.qualfacul.hades.exceptions.ConversationMessageNotFoundException;
import com.qualfacul.hades.exceptions.ConversationNotFoundException;

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
	private ConversationMessageToConversationMessageDTOConverter messageConverter;
	
	@Autowired
	private ReplyMessageDTOToConversationMessageConverter replyMessageConverter;
	
	@Post(value = "/conversations/new", responseStatus = CREATED)
	public ConversationDTO newConversation(@RequestBody @Valid ConversationStartDTO conversationStartDTO){
		Conversation conversation = conversationStartDTOConverter.convert(conversationStartDTO);
		conversationRepository.save(conversation);
		return conversationConverter.fromConversation(conversation).convert();
	}
	
	@Post(value = "/conversations/reply/new", responseStatus = CREATED)
	public ConversationMessageDTO newReply(@RequestBody @Valid ReplyMessageDTO replyMessageDTO){
		ConversationMessage conversationMessage = replyMessageConverter.convert(replyMessageDTO);
		messageRepository.save(conversationMessage);
		return messageConverter.convert(conversationMessage);
	}
	
	@Get("/conversations/{id}")
	public ConversationDTO getConversation(@PathVariable Long id){
		return conversationRepository.findById(id)
				.map(conversation -> conversationConverter
										.fromConversation(conversation)
										.withMessages()
										.convert())
				.orElseThrow(ConversationNotFoundException::new);
	}
	
	@Get("/conversations/{conversationId}/message/{id}")
	public ConversationMessageDTO getMessage(@PathVariable Long conversationId, @PathVariable Long id){
		return messageRepository.findByConversationIdAndId(conversationId, id)
				.map(conversationMessage -> messageConverter.convert(conversationMessage))
				.orElseThrow(ConversationMessageNotFoundException::new);
	}
}
