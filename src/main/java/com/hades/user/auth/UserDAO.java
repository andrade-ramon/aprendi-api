package com.hades.user.auth;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

	@PersistenceContext
	private EntityManager manager;

	public void create(User user) {
		manager.persist(user);
	}

	public User find(String email, String password) {
		return manager.createQuery("select u from User u where u.email = :email "
				+ "and u.password = :password", User.class)
				.setParameter("email", email)
				.setParameter("password", password)
				.getSingleResult();

	}

	public User loadUserByUsername(String login) throws UsernameNotFoundException {
		return manager.createQuery("select u from User u where u.email = :email", User.class)
				.setParameter("email", login)
				.getSingleResult();
	}
}
