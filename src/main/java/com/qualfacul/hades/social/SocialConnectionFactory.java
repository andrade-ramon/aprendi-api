package com.qualfacul.hades.social;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;

@Configuration
class SocialConnectionFactory {

	@Resource
	private Environment env;

	@Bean
	FacebookConnectionFactory facebookConnectionFactory() {
		return new FacebookConnectionFactory(env.getProperty("facebook.clientId"), env.getProperty("facebook.clientSecret"));
	}
}
