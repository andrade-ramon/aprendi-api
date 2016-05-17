package com.hades.social;

import java.util.Optional;

import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = SocialLogin.class, idClass = Long.class)
public interface SocialLoginRepository {
	SocialLogin save(SocialLogin socialLogin);
	
	Optional<SocialLogin> findBySocialId(String socialId);
}
