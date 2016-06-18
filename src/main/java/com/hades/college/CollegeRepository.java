package com.hades.college;

import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = College.class, idClass = Long.class)
public interface CollegeRepository {

	College save(College college);
	
	Optional<College> findByCnpj(String cnpj);

	Optional<College> findById(Long collegeId);

	Long count();
	
}
