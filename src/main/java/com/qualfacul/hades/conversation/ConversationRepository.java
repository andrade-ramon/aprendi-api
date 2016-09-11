package com.qualfacul.hades.conversation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Conversation.class, idClass = Long.class)
public interface ConversationRepository {
	
	List<Conversation> findAllByCollegeId(Long collegeId);
	
	Conversation save(Conversation conversation);

	Optional<Conversation> findById(Long id);


}
