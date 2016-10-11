package com.qualfacul.hades.mail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import com.qualfacul.hades.annotation.WebService;

@WebService
public class SmtpEmailSender {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(SmtpEmailSender.class);
	private static final String UTF_8_CHARSET = "UTF-8";

	@Value("${email.debug}")
	private boolean emailDebug;
	
	@Value("${email.hostname}")
	private String hostName;

	@Value("${email.port}")
	private String port;

	@Value("${email.name}")
	private String name;

	@Value("${email.username}")
	private String userName;

	@Value("${email.password}")
	private String password;
	
	private String to;
	private String subject;
	private String message;
	
	public Subject to(String email) {
		to = email;
		return new Subject();
	}
	
	public class Subject {
		
		public MessageContent withSubject(String sub) {
			subject = sub;
			return new MessageContent();
		}
	}
	
	public class MessageContent {
		
		public Sender withMessage(String messageContent) {
			message = messageContent;
			return new Sender();
		}
	}
	
	public class Sender {
		public void send() {
			HtmlEmail htmlEmail = new HtmlEmail();
			htmlEmail.setDebug(emailDebug);
			htmlEmail.setSSLOnConnect(true);
			htmlEmail.setHostName(hostName);
			htmlEmail.setSslSmtpPort(port);
			htmlEmail.setAuthenticator(new DefaultAuthenticator(userName, password));
			htmlEmail.setSubject(subject);
			htmlEmail.setCharset(UTF_8_CHARSET);
			try {
				htmlEmail.setFrom(userName, name);
				htmlEmail.setHtmlMsg(message);
				htmlEmail.addTo(to);
				htmlEmail.send();
			} catch (EmailException e) {
				LOGGER.warn("Could not send email to: {}", to);
			}
		}
	}
}
