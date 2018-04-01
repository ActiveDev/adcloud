package com.activedevsolutions.cloud.aclservice.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.activedevsolutions.cloud.core.dao.AbstractDao;

/**
 * Provides base functionality for a jdbc dao.
 *
 * @param <T> is the resource to expose the CRUD operations to.
 */
public abstract class JdbcDao<T> extends AbstractDao<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(JdbcDao.class);
	
	// Templates to use in DAO
	protected JdbcTemplate jdbcTemplate;
	protected NamedParameterJdbcTemplate namedJdbcTemplate;
	
	// Attribute Constants
	protected static final String ATTRIBUTE_ID = "id";
	protected static final String ATTRIBUTE_NAME = "name";
	protected static final String ATTRIBUTE_DESCRIPTION = "description";
	protected static final String ATTRIBUTE_ENDPOINT = "endpoint";
	protected static final String ATTRIBUTE_FIRST_NAME = "first_name";
	protected static final String ATTRIBUTE_LAST_NAME = "last_name";
	protected static final String ATTRIBUTE_USERID = "user_id";
	
	// Query for getting the id of the newly created record
	private static final String SQL_GET_LAST_ID = "SELECT LAST_INSERT_ID();";
	
	/**
	 * Constructor.
	 * 
	 * @param template is the standard jdbc template
	 * @param namedTemplate is used where an IN clause is needed
	 */
	public JdbcDao(JdbcTemplate template, NamedParameterJdbcTemplate namedTemplate) {
		this.jdbcTemplate = template;
		this.namedJdbcTemplate = namedTemplate;
	}
	
	/**
	 * Gets the last id inserted.
	 * 
	 * @return int containing the last id
	 */
	protected int getLastId() {
		int id = jdbcTemplate.queryForObject(SQL_GET_LAST_ID, Integer.class);
		LOGGER.debug("Last id: {}", id);
		
		return id;
	}

	/**
	 * Create resource.
	 * 
	 * @param item is the resource
	 * @return int representing the id of the created resource
	 */
	@Override
	public int create(T item) {
		LOGGER.debug("[START] Inserting: {}", item);
		
		createQuery(item);
		
		LOGGER.debug("[END] Inserting");
		return getLastId();
	}
}
