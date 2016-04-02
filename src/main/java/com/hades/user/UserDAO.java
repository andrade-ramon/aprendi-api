package com.hades.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import com.hades.login.LoginInfo;
import com.hades.login.LoginInfoDAO;

@Repository
public class UserDAO implements UserRepository {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private LoginInfoDAO loginInfoDAO;

	public LoginInfo createUserAndLoginFor(UserDTO userDTO) {
		LoginInfo loginInfo = new LoginInfo(userDTO.getEmail(), userDTO.getPassword(), null);
		loginInfoDAO.persist(loginInfo);
		
		User user = new User(null, userDTO.getName(), userDTO.getEmail());
		saveAndFlush(user);
		
		return loginInfo;
	}

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public List<User> findAll(Sort sort) {
		return userRepository.findAll(sort);
	}

	@Override
	public List<User> findAll(Iterable<Long> ids) {
		return userRepository.findAll(ids);
	}

	@Override
	public <S extends User> List<S> save(Iterable<S> entities) {
		return userRepository.save(entities);
	}

	@Override
	public void flush() {
		userRepository.flush();
	}

	@Override
	public <S extends User> S saveAndFlush(S entity) {
		return userRepository.saveAndFlush(entity);
	}

	@Override
	public void deleteInBatch(Iterable<User> entities) {
		userRepository.deleteInBatch(entities);
	}

	@Override
	public void deleteAllInBatch() {
		userRepository.deleteAllInBatch();
	}

	@Override
	public User getOne(Long id) {
		return userRepository.getOne(id);
	}

	@Override
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
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
}
