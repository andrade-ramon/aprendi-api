package com.hades.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.hades.annotation.Post;
import com.hades.configuration.security.TokenAuthenticationService;
import com.hades.login.LoginInfo;
import com.hades.login.LoginInfoDTO;
import com.hades.login.LoginInfoRepository;
import com.hades.login.LoginOrigin;

@RestController
public class AdminController {
	
	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private LoginInfoRepository loginInfoRepository;
	
	@Autowired
	private TokenAuthenticationService tokenService;

	@Transactional
	@Post("/admin/register")
	@ResponseStatus(HttpStatus.CREATED)
	public LoginInfoDTO register(@Valid @RequestBody AdminDTO adminDTO) {
		LoginInfo loginInfo = new LoginInfo(adminDTO.getEmail(), adminDTO.getPassword(), LoginOrigin.ADMIN);
		loginInfoRepository.save(loginInfo);

		Admin admin = new Admin(adminDTO.getName(), adminDTO.getEmail());
		adminRepository.save(admin);
		
		tokenService.createTokenFor(loginInfo);

		return new LoginInfoDTO(loginInfo);
	}
}