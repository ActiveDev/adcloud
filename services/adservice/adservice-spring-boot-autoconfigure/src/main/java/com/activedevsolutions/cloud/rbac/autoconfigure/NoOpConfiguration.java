package com.activedevsolutions.cloud.rbac.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@ConditionalOnExpression("!${adservice.security.enabled:false}")
public class NoOpConfiguration  extends WebSecurityConfigurerAdapter {

	/**
	 * Allows all requests.
	 * 
	 * {@inheritDoc}
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
			.antMatchers("/").permitAll()
			.and()
			.csrf().disable()
			.httpBasic().disable();
	}
}