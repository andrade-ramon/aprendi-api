package com.qualfacul.hades.conversation;

import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = ConversationMessage.class, idClass = Long.class)
public interface ConversationMessageRepository {

	ConversationMessage save(ConversationMessage conversationMessage);
	
	Optional<ConversationMessage> findByConversationIdAndId(long conversationId, long id);
	
}
