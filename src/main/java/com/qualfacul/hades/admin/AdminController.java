package com.qualfacul.hades.admin;

import static org.springframework.http.HttpStatus.CREATED;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.Post;
import com.qualfacul.hades.configuration.security.TokenAuthenticationService;
import com.qualfacul.hades.login.LoginInfo;
import com.qualfacul.hades.login.LoginInfoDTO;
import com.qualfacul.hades.login.LoginOrigin;

@RestController
public class AdminController {
	
	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private TokenAuthenticationService tokenService;

	@Transactional
	@Post(value = "/admin/register", responseStatus = CREATED)
	public LoginInfoDTO register(@Valid @RequestBody AdminDTO adminDTO) {
		LoginInfo loginInfo = new LoginInfo(adminDTO.getEmail(), adminDTO.getPassword(), LoginOrigin.ADMIN);
		Admin admin = new Admin(adminDTO.getName(), adminDTO.getEmail());
		admin.setLoginInfo(loginInfo);
		
		adminRepository.save(admin);
		
		tokenService.createTokenFor(loginInfo);

		return new LoginInfoDTO().from(loginInfo);
	}
}