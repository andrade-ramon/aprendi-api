package com.qualfacul.hades.passwordrecovery;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.qualfacul.hades.annotation.Post;
import com.qualfacul.hades.annotation.PublicEndpoint;
import com.qualfacul.hades.configuration.security.TokenAuthenticationService;
import com.qualfacul.hades.crypt.Base64Utils;
import com.qualfacul.hades.exceptions.FacebookAccountException;
import com.qualfacul.hades.exceptions.UsernameNotFoundException;
import com.qualfacul.hades.login.LoginInfo;
import com.qualfacul.hades.login.LoginInfoRepository;
import com.qualfacul.hades.mail.SmtpEmailSender;

@RestController
public class PasswordRecoveryController {
	
	@Autowired
	private TokenAuthenticationService tokenAuthenticationService;
	@Autowired
	private LoginInfoRepository loginInfoRepository;
	@Autowired
	private SmtpEmailSender emailSender;
	
	@PublicEndpoint
	@Post("/password/reset")
	public void requestToken(@RequestBody @NotBlank PasswordChangeDTO passwordChangeDTO) {
		LoginInfo loginInfo = loginInfoRepository.findByLogin(passwordChangeDTO.getEmail())
						.orElseThrow(UsernameNotFoundException::new);

		if (loginInfo.isFromFacebook()) {
			throw new FacebookAccountException();
		}

		tokenAuthenticationService.createTokenFor(loginInfo);
		
		String userToken = Base64Utils.encode(loginInfo.getToken());
		StringBuilder message = new StringBuilder();
		message.append("Olá!<br/>Para redefinir sua senha, clique ");
		message.append("<a href=\"http://dev.qualfacul.com:9000/redefinir-senha/" + userToken + "\">");
		message.append("aqui</a><br/>");
		message.append("Este link é válido por 24 horas, após esse período, será necessário ");
		message.append("solicitar um novo link através do site.");

		emailSender.to(loginInfo.getLogin())
				   .withSubject("Recuperação de Senha")
				   .withMessage(message.toString())
				   .send();

	}
	
	@PublicEndpoint
	@Post("/password/change")
	public void changePassword(@RequestBody @Valid PasswordRecoveryDTO passwordRecoveryDTO) {
		String token = Base64Utils.decode(passwordRecoveryDTO.getToken());
		
		LoginInfo loginInfo = tokenAuthenticationService.getUserFromToken(token)
						.orElseThrow(UsernameNotFoundException::new);
		loginInfo.setPassword(passwordRecoveryDTO.getPassword());
		loginInfoRepository.save(loginInfo);
	}
}
