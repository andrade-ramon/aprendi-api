package com.qualfacul.hades.college;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

import com.qualfacul.hades.login.LoginInfo;

@RepositoryDefinition(domainClass = College.class, idClass = Long.class)
public interface CollegeRepository {

	College save(College college);
	
	Optional<College> findById(long id);
	
	Optional<College> findByMecId(long mecId);
	
	Optional<College> findByCnpj(String cnpj);
	
	List<College> findAll();

	Optional<College> findByLoginInfo(LoginInfo loginInfo);	
}
