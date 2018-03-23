package com.activedevsolutions.cloud.templateservice.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides base functionality for a jdbc dao.
 *
 * @param <T> is the resource to expose the CRUD operations to.
 */
public abstract class AbstractDao<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractDao.class);
			
	/**
	 * Create resource.
	 * 
	 * @param item is the resource
	 * @return int representing the id of the created resource
	 */
	public int create(T item) {
		LOGGER.debug("[START] Inserting: {}", item);
		
		int result = createQuery(item);
		
		LOGGER.debug("[END] Inserting");
		return result;
	}

	/**
	 * Runs the create query.
	 * 
	 * @param item is the resource
	 */
	protected abstract int createQuery(T item);

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
