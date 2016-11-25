package com.qualfacul.hades.admin;

import static org.springframework.http.HttpStatus.CREATED;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.Delete;
import com.qualfacul.hades.annotation.OnlyAdmin;
import com.qualfacul.hades.annotation.Post;
import com.qualfacul.hades.configuration.security.TokenAuthenticationService;
import com.qualfacul.hades.exceptions.AdminCannotDeleteHimselfException;
import com.qualfacul.hades.exceptions.AdminNotFoundException;
import com.qualfacul.hades.login.LoggedUserManager;
import com.qualfacul.hades.login.LoginInfo;
import com.qualfacul.hades.login.LoginInfoDTO;
import com.qualfacul.hades.login.LoginOrigin;

@RestController
public class AdminController {

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private TokenAuthenticationService tokenService;

	@Autowired
	private LoggedUserManager loggedUserManager;

	@OnlyAdmin
	@Post(value = "/admin", responseStatus = CREATED)
	public LoginInfoDTO createAdmin(@Valid @RequestBody AdminDTO adminDTO) {
		LoginInfo loginInfo = new LoginInfo(adminDTO.getEmail(), adminDTO.getPassword(), LoginOrigin.ADMIN);
		Admin admin = new Admin(adminDTO.getName(), adminDTO.getEmail());
		admin.setLoginInfo(loginInfo);

		adminRepository.save(admin);

		tokenService.createTokenFor(loginInfo);

		return new LoginInfoDTO().from(loginInfo);
	}

	@OnlyAdmin
	@Delete(value = "/admin/{adminId}")
	public void deleteAdmin(@PathVariable Long adminId) {
		Admin admin = adminRepository.findById(adminId).orElseThrow(AdminNotFoundException::new);
		if (loggedUserManager.getLoginInfo().getId() == admin.getLoginInfo().getId()) {
			throw new AdminCannotDeleteHimselfException();
		}
		adminRepository.delete(admin);
	}
}