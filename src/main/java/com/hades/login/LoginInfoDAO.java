package com.hades.login;

import static org.hibernate.criterion.Restrictions.eq;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

@Repository
public class LoginInfoDAO {

	@PersistenceContext
	private EntityManager manager;

	@Transactional //FIXME
	public Optional<LoginInfo> findBy(String login) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(LoginInfo.class);
		LoginInfo loginInfo = (LoginInfo) criteria
						.add(eq("login", login))
						.setMaxResults(1)
						.uniqueResult();

		return Optional.ofNullable(loginInfo);
	}

	@Transactional
	public void create(LoginInfo loginInfo) {
		manager.persist(loginInfo);
		manager.flush();
	}
}
