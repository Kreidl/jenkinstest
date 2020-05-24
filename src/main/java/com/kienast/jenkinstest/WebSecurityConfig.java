package com.kienast.jenkinstest;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

public class WebSecurityConfig extends WebSecurityConfigurerAdapter  {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin().disable().headers().contentTypeOptions();
		// TODO Auto-generated method stub
		/*http.authorizeRequests()
		.antMatchers("/").permitAll()
		.and().formLogin().headers().contentTypeOptions();*/
	}


}
