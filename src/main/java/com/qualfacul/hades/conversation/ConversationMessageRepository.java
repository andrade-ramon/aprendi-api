package com.qualfacul.hades.conversation;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = ConversationMessage.class, idClass = Long.class)
public interface ConversationMessageRepository {

	ConversationMessage save(ConversationMessage conversationMessage);
	
}
