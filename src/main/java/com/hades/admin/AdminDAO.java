package com.hades.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hades.login.LoginInfo;
import com.hades.login.LoginInfoDAO;
import com.hades.admin.Admin;
import com.hades.admin.AdminDTO;
import com.hades.admin.AdminRepository;

@Repository
public class AdminDAO implements AdminRepository{

	@Autowired
	private AdminRepository adminRepository;
	
	@Autowired
	private LoginInfoDAO loginInfoDAO;

	public LoginInfo createAdminFrom(AdminDTO adminDTO) {
		LoginInfo loginInfo = new LoginInfo(adminDTO.getEmail(), adminDTO.getPassword());
		loginInfoDAO.create(loginInfo);
		
		Admin admin = new Admin(adminDTO.getName(), adminDTO.getEmail(), adminDTO.getPassword());
		save(admin);
		
		return loginInfo;
	}

	@Override
	public <S extends Admin> S save(S entity) {
		return adminRepository.save(entity);
	}

	@Override
	public Admin findOne(Long id) {
		return adminRepository.findOne(id);
	}

	@Override
	public boolean exists(Long id) {
		return adminRepository.exists(id);
	}

	@Override
	public long count() {
		return adminRepository.count();
	}

	@Override
	public void delete(Long id) {
		adminRepository.delete(id);
	}

	@Override
	public void delete(Admin entity) {
		adminRepository.delete(entity);
	}

	@Override
	public void delete(Iterable<? extends Admin> entities) {
		adminRepository.delete(entities);
	}

	@Override
	public void deleteAll() {
		adminRepository.deleteAll();
	}

	@Override
	public <S extends Admin> Iterable<S> save(Iterable<S> entities) {
		return adminRepository.save(entities);
	}

	@Override
	public Iterable<Admin> findAll() {
		return adminRepository.findAll();
	}

	@Override
	public Iterable<Admin> findAll(Iterable<Long> ids) {
		return adminRepository.findAll(ids);
	}
}
