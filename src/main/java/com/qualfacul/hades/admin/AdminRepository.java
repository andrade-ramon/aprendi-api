package com.qualfacul.hades.admin;

import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Admin.class, idClass = Long.class)
public interface AdminRepository {
	
	Optional<Admin> findById(long id);

	Admin save(Admin admin);
	
	void delete(Admin admin);

}