package com.activedevsolutions.cloud.templateservice.dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class DaoConfiguration {
	@Bean
	@Order(1)
	public InMemoryDao dao() {
		return new InMemoryDao();
	}
}
