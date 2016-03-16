package com.hades.user.auth;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

@Repository
public class UserDAO {

	@PersistenceContext
	private EntityManager manager;

	public void save(User user) {
		manager.persist(user);
	}

	public User findBy(String email, String password) {
		return manager.createQuery("select u from " +  User.class.getSimpleName() + " u where u.email = :email and u.password = :password", User.class)
						.setParameter("email", email)
						.setParameter("password", password)
						.getSingleResult();
	}

	public User findBy(String login) throws UsernameNotFoundException {
		return manager.createQuery("select u from " +  User.class.getSimpleName() + " u where u.email = :email", User.class)
						.setParameter("email", login)
						.getSingleResult();
	}
}
