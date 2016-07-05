package com.qualfacul.hades.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.interceptor.StatelessAuthenticationInterceptor;

@Configuration
@WebComponent
class WebConfiguration extends WebMvcConfigurerAdapter {

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(StatelessAuthenticationInterceptor());
	} 
	
	@Bean
	public StatelessAuthenticationInterceptor StatelessAuthenticationInterceptor() {
	    return new StatelessAuthenticationInterceptor();
	}
}
