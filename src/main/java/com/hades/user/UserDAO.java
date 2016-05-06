package com.hades.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hades.login.LoginInfo;
import com.hades.login.LoginInfoDAO;

@Repository
public class UserDAO implements UserRepository {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginInfoDAO loginInfoDAO;

	public LoginInfo createFrom(UserDTO userDTO) {
		LoginInfo loginInfo = new LoginInfo(userDTO.getEmail(), userDTO.getPassword(), userDTO.getLoginOrigin());
		loginInfoDAO.create(loginInfo);
		User user = new User(userDTO.getName(), userDTO.getEmail(), userDTO.getLoginOrigin());
		save(user);

		return loginInfo;
	}

	@Override
	public <S extends User> S save(S entity) {
		return userRepository.save(entity);
	}

	@Override
	public User findOne(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public boolean exists(Long id) {
		return userRepository.exists(id);
	}

	@Override
	public long count() {
		return userRepository.count();
	}

	@Override
	public void delete(Long id) {
		userRepository.delete(id);
	}

	@Override
	public void delete(User entity) {
		userRepository.delete(entity);
	}

	@Override
	public void delete(Iterable<? extends User> entities) {
		userRepository.delete(entities);
	}

	@Override
	public void deleteAll() {
		userRepository.deleteAll();
	}

	@Override
	public <S extends User> Iterable<S> save(Iterable<S> entities) {
		return userRepository.save(entities);
	}

	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Iterable<User> findAll(Iterable<Long> ids) {
		return userRepository.findAll(ids);
	}
}