package com.qualfacul.hades.conversation;

import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Conversation.class, idClass = Long.class)
public interface ConversationRepository {
	
	Conversation save(Conversation conversation);

	Optional<Conversation> findById(Long id);

}
