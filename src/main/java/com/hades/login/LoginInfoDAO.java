package com.hades.login;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

@Repository
public class LoginInfoDAO {

	@PersistenceContext
	private EntityManager manager;

	public Optional<LoginInfo> findBy(String login, String password) {
		List<LoginInfo> resultList = manager
				.createQuery("select u from " + LoginInfo.class.getSimpleName()
						+ " u where u.login = :login and u.password = :password", LoginInfo.class)
				.setParameter("login", login).setParameter("password", password).getResultList();
		if (resultList.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(resultList.get(0));
	}

	public Optional<LoginInfo> findBy(String login) {
		List<LoginInfo> resultList = manager
				.createQuery("select u from " + LoginInfo.class.getSimpleName() + " u where u.login = :login",
						LoginInfo.class)
				.setParameter("login", login).getResultList();
		if (resultList.isEmpty()) {
			return Optional.empty();
		}
		return Optional.of(resultList.get(0));
	}

	@Transactional
	public void create(LoginInfo loginInfo) {
		manager.persist(loginInfo);
		manager.flush();
	}
}
