package com.qualfacul.hades.conversation;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Conversation.class, idClass = Long.class)
public interface ConversationRepository {
	
	Conversation save(Conversation conversation);

}
