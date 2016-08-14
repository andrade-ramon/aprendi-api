package com.qualfacul.hades.passwordrecovery;

import java.util.Date;
import java.util.Optional;

import javax.validation.Valid;

import com.qualfacul.hades.configuration.security.TokenAuthenticationService;
import com.qualfacul.hades.crypt.Base64Utils;
import com.qualfacul.hades.exceptions.FacebookAccountException;
import com.qualfacul.hades.exceptions.UsernameNotFoundException;
import com.qualfacul.hades.login.LoginInfo;
import com.qualfacul.hades.login.LoginInfoRepository;
import com.qualfacul.hades.mail.EmailAddress;
import com.qualfacul.hades.mail.SmtpEmailSender;

import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.PermitEndpoint;
import com.qualfacul.hades.annotation.Post;

@RestController
public class PasswordRecoveryController {
	//EXPIRATION_TIME must be in minutes
	public static int EXPIRATION_TIME = 60*24;
	
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;
	@Autowired
	private LoginInfoRepository loginInfoRepository;
	@Autowired
	private SmtpEmailSender emailDelivery;
	
	@PermitEndpoint
	@Post("/passwordrecovery/request")
	public boolean requestToken(@RequestBody @NotBlank String login) throws FacebookAccountException {
		Optional<LoginInfo> optionalLoginInfo = loginInfoRepository.findByLogin(login);
		if (optionalLoginInfo.isPresent()){
			LoginInfo loginInfo = optionalLoginInfo.get();
			if (loginInfo.isFromFacebook()){
				throw new FacebookAccountException();
			}
			Date expirationDate = new Date();
			expirationDate = DateUtils.addMinutes(expirationDate, EXPIRATION_TIME);
			String userToken = tokenAuthenticationService.createTokenFor(loginInfo, expirationDate);
			userToken = Base64Utils.encode(userToken);
			StringBuilder message = new StringBuilder();
			message.append("Ola!<br/>Para redefinir sua senha, clique ");
			message.append("<a href=\"http://dev.qualfacul.com:9000/redefinirsenha/"+userToken+"\">");
			message.append("aqui</a><br/>");
			message.append("Este link e valido por 24h, apos esse periodo, sera necessario ");
			message.append("solicitar um novo link atraves do site.");
			EmailAddress email = new EmailAddress(loginInfo.getLogin());
			return emailDelivery.send("Recuperacao de Senha", message, email);
		}
		return true;
	}
	
	@PermitEndpoint
	@Post("/passwordrecovery/change")
	public boolean changePassword(@RequestBody @Valid PasswordRecoveryDTO passwordRecoveryDTO) throws FacebookAccountException, UsernameNotFoundException {
		String token = Base64Utils.decode(passwordRecoveryDTO.getToken());
		Optional<LoginInfo> optionalLoginInfo = tokenAuthenticationService.getUserFromToken(token);
		if (!optionalLoginInfo.isPresent()){
			throw new UsernameNotFoundException();
		}
		LoginInfo loginInfo = optionalLoginInfo.get();
		if (loginInfo.isFromFacebook()){
			throw new FacebookAccountException();
		}
		loginInfo.setPassword(passwordRecoveryDTO.getPassword());
		try{
			loginInfoRepository.save(loginInfo);
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
}
