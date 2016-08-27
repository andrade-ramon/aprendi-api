package com.qualfacul.hades.login;

import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = LoginInfo.class, idClass = Long.class)
public interface LoginInfoRepository {
	
	LoginInfo save(LoginInfo loginInfo);

	Optional<LoginInfo> findByLogin(String login);

}
