package com.hades.admin;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Admin.class, idClass = Long.class)
public interface AdminRepository {

	Admin save(Admin admin);

}