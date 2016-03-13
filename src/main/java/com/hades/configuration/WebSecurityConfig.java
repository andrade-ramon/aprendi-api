package com.hades.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hades.configuration.security.TokenAuthenticationService;
import com.hades.filter.StatelessAuthenticationFilter;
import com.hades.user.auth.UserDAO;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Value("${jwt.secret}")
	private String SECRET;

	@Autowired
	private UserDAO userDAO;

	WebSecurityConfig() {
		super(true);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		TokenAuthenticationService tokenService = new TokenAuthenticationService(SECRET, userDAO);

		http
			.anonymous()
			.and().authorizeRequests()
			.antMatchers("/").permitAll()
			.antMatchers("/login").permitAll()
			.anyRequest().authenticated()
			.and().addFilterBefore(new StatelessAuthenticationFilter(tokenService),
						UsernamePasswordAuthenticationFilter.class)	
;
	}

}