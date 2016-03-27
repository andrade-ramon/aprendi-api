package com.hades.user.auth;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

	@PersistenceContext
	private EntityManager manager;

	public void save(User user) {
		manager.persist(user);
	}

	public Optional<User> findBy(String email, String password) {
		List<User> resultList = manager
				.createQuery("select u from " + User.class.getSimpleName()
						+ " u where u.email = :email and u.password = :password", User.class)
				.setParameter("email", email).setParameter("password", password)
				.getResultList();
		if (resultList.isEmpty()) {
			return Optional.empty();
		}
		return Optional.ofNullable(resultList.get(0));
	}

	public Optional<User> findBy(String login) {
		List<User> resultList = manager
			.createQuery("select u from " + User.class.getSimpleName() + 
					" u where u.email = :email", User.class)
			.setParameter("email", login)
			.getResultList();
		if(resultList.isEmpty()){
			return Optional.empty();
		}
		return Optional.ofNullable(resultList.get(0));
	}
}
