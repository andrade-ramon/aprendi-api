package com.qualfacul.hades.configuration;

import static java.util.Arrays.asList;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module.Feature;
import com.qualfacul.hades.annotation.WebComponent;
import com.qualfacul.hades.annotation.WebConfiguration;
import com.qualfacul.hades.interceptor.StatelessAuthenticationInterceptor;

@WebConfiguration
@WebComponent
class WebConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private StatelessAuthenticationInterceptor statelessAuthenticationInterceptor;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**");
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(statelessAuthenticationInterceptor);
	}

	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		final JsonStringHttpMessageConverter stringConverter = new JsonStringHttpMessageConverter();
		
		final ObjectMapper objectMapper = new ObjectMapper();
		final Hibernate4Module module = new Hibernate4Module();
		
		module.enable(Feature.FORCE_LAZY_LOADING);
		objectMapper.registerModule(module);
		converter.setObjectMapper(objectMapper);
		stringConverter.setSupportedMediaTypes(asList(APPLICATION_JSON));
		converters.add(stringConverter);
		converters.add(converter);
		super.configureMessageConverters(converters);
	}
}
