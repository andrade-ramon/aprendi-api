package com.qualfacul.hades.conversation;

import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Message.class, idClass = Long.class)
public interface MessageRepository {

	Optional<Message> findByIdAndConversationId(Long id, Long conversationId);

	Message save(Message message);
}
