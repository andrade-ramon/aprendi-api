package com.qualfacul.hades.user;

import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = User.class, idClass = Long.class)
public interface UserRepository {

	User save(User user);
	
	Optional<User> findById(Long id);

	Optional<User> findByEmail(String email);

}