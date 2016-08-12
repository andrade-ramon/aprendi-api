package com.qualfacul.hades.mail;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.springframework.beans.factory.annotation.Value;

import com.qualfacul.hades.annotation.WebService;

@WebService
public class SmtpEmailSender {
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
	
	@Value("${email.charset}")
	private String charset;

	public boolean send(String subject, StringBuilder message, EmailAddress...emails){
		HtmlEmail htmlEmail = new HtmlEmail();
		htmlEmail.setSSLOnConnect(true);
		htmlEmail.setHostName(hostName);
		htmlEmail.setSslSmtpPort(port);
		htmlEmail.setAuthenticator(new DefaultAuthenticator(userName, password));
		try{
			htmlEmail.setFrom(userName, name);
			htmlEmail.setDebug(true); 
			htmlEmail.setSubject(subject);
		    htmlEmail.setHtmlMsg(message.toString());
		    for (EmailAddress email : emails) {
				if (email.getName().equals("")){
					htmlEmail.addTo(email.getEmail());
				}else{
					htmlEmail.addTo(email.getEmail(), email.getName());
				}
			}
		    htmlEmail.setCharset(charset);
		    htmlEmail.send();
		    return true;
		}catch(EmailException e){
			e.printStackTrace();
			return false;
		}
	}
}
