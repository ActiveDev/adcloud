package com.activedevsolutions.cloud.aclservice.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

@Configuration
public class DaoConfiguration {
	@Bean
	@Order(1)
	public JdbcTemplate jdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
		return new JdbcTemplate(dataSource);
	}

	@Bean
	@Order(2)
	public NamedParameterJdbcTemplate namedJdbcTemplate(@Qualifier("dataSource") DataSource dataSource) {
		return new NamedParameterJdbcTemplate(dataSource);
	}

	@Bean
	@Order(3)
	public PermissionDao PermissionDAO(@Autowired JdbcTemplate jdbcTemplate, 
			@Autowired NamedParameterJdbcTemplate namedJdbcTemplate) {
		return new PermissionDao(jdbcTemplate, namedJdbcTemplate);
	}
	
	@Bean
	@Order(4)
	public RoleDao RoleDAO(@Autowired JdbcTemplate jdbcTemplate, 
			@Autowired NamedParameterJdbcTemplate namedJdbcTemplate) {
		return new RoleDao(jdbcTemplate, namedJdbcTemplate);
	}

	@Bean
	@Order(5)
	public GroupDao GroupDAO(@Autowired JdbcTemplate jdbcTemplate, 
			@Autowired NamedParameterJdbcTemplate namedJdbcTemplate) {
		return new GroupDao(jdbcTemplate, namedJdbcTemplate);
	}
	
	@Bean
	@Order(6)
	public UserDao UserDAO(@Autowired JdbcTemplate jdbcTemplate, 
			@Autowired NamedParameterJdbcTemplate namedJdbcTemplate) {
		return new UserDao(jdbcTemplate, namedJdbcTemplate);
	}
}
