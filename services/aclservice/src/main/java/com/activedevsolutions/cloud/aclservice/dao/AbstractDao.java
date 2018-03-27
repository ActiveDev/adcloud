package com.activedevsolutions.cloud.aclservice.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Provides base functionality for a jdbc dao.
 *
 * @param <T> is the resource to expose the CRUD operations to.
 */
public abstract class AbstractDao<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDao.class);
	
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
	public AbstractDao(JdbcTemplate template, NamedParameterJdbcTemplate namedTemplate) {
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
	public int create(T item) {
		LOGGER.debug("[START] Inserting: {}", item);
		
		createQuery(item);
		
		LOGGER.debug("[END] Inserting");
		return getLastId();
	}

	/**
	 * Runs the create query.
	 * 
	 * @param item is the resource
	 */
	protected abstract void createQuery(T item);

	/**
	 * Update resource.
	 * 
	 * @param item is the resource
	 * @return int representing the number of records affected
	 */
	public int update(T item) {
		LOGGER.debug("[START] Updating: {}", item);

		int result = updateQuery(item);
		
		LOGGER.debug("[END] Updating");
		return result;
	}

	/**
	 * Runs the update query.
	 * 
	 * @param item is the resource
	 * @return int representing the number of records affected
	 */
	protected abstract int updateQuery(T item);

	/**
	 * Delete resource
	 * 
	 * @param item is the resource
	 * @return int representing the number of records affected
	 */
	public int delete(int id) {
		LOGGER.debug("[START] Deleting: {}", id);
		
		deleteChildren(id);
		int result = deleteQuery(id);
		
		LOGGER.debug("[END] Deleting");
		return result;
	}
	
	/**
	 * Runs the delete query.
	 * 
	 * @param item is the resource
	 * @return int representing the number of records affected
	 */
	protected abstract int deleteQuery(int id);

	/**
	 * Gets the resource.
	 * 
	 * @return List<T> containing the list of retrieved objects
	 */
	public List<T> getList() {
		LOGGER.debug("[START] Get items.");
		
		List<T> items = null;		
		List<Map<String, Object>> results = getListQuery();
		items = parse(results);
		
		LOGGER.debug("[END] Get items.");
		
		return items;
	}
	
	/**
	 * Runs the select query.
	 * 
	 * @return List containing the query results
	 */
	protected abstract List<Map<String, Object>> getListQuery();
	
	/**
	 * Gets the resource.
	 * 
	 * @param item is the resource
	 * @return T containing the retrieved object
	 */
	public T getItem(int id) {
		LOGGER.debug("[START] Get item. {}", id);
		
		List<T> items = null;		
		List<Map<String, Object>> results = getItemQuery(id);
		items = parse(results);
		T result = null;
		
		if (!items.isEmpty()) {
			result = items.get(0);
		} // end if
		
		LOGGER.debug("[END] Get item. {}", result);
		
		return result;
	}
	
	/**
	 * Runs the select query.
	 * 
	 * @param item is the resource
	 * @return List containing the query results
	 */
	protected abstract List<Map<String, Object>> getItemQuery(int id);

	/**
	 * Parse the group from the query results.
	 * 
	 * @param results from the query
	 * @return List<Group> results parsed into the list
	 */
	protected List<T> parse(List<Map<String, Object>> results) {
		List<T> items = new ArrayList<>();
		
		for (Map<String, Object> row : results) {
			T item = assembleItem(row);
			items.add(item);
		} // end for
		
		return items;
	}
	
	/**
	 * Will assemble the object based on the row returned from the query.
	 * 
	 * @param row is the collection representing a single row in the query
	 * @return T containing the object that was assembled from the row
	 */
	protected abstract T assembleItem(Map<String, Object> row);
	
	public abstract int addChildren(int id, List<?> children);
	public abstract int deleteChildren(int id);
}
