package com.activedevsolutions.cloud.templateservice;


import java.net.URI;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.activedevsolutions.cloud.templateservice.dao.AbstractDao;
import com.activedevsolutions.cloud.templateservice.exception.ResourceNotFoundException;

/**
 * An abstract class that follows as much of the REST conventions as it can. 
 * It will provide base functionality for CRUD operations on item T.
 * 
 * At the moment, it uses one exception handler to return 400 Bad Request.
 * This is because each method will update the http status as it sees fit.
 * The alternative is for each validation to throw a specific exception and have
 * an exception handler that will return the appropriate http status based on the
 * exception class.
 */
public abstract class AbstractController<T> {
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractController.class);
	protected static final String RESOURCE = "";
	
	@Autowired
	//TODO This should be an interface to further abstract the implementation
	protected AbstractDao<T> dao;
	
	//TODO Improve exception handling as the runtime exceptions are not being caught properly
	
	/**
	 * Creates a item.
	 * 
	 * @param item is a json representation of the T object
	 * @return item object containing the newly created item
	 */
	@RequestMapping(value = RESOURCE, method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<T> add(@RequestBody T item, UriComponentsBuilder ucb) {
 		LOGGER.info("[START] Adding {}", item);
		// Create the item object with the values passed in.
		
		// Save item
		int id = dao.create(item);
		T newItem = setupItem(item, id);
		
		// Set the location for the new object
		HttpHeaders headers = new HttpHeaders();
		//TODO This uri is missing the correct resource
		URI locationUri = ucb.path("/v1.0" + RESOURCE + "/").path(String.valueOf(id)).build().toUri();
		headers.setLocation(locationUri);
		
		LOGGER.info("[END] Adding {}", newItem);
		return new ResponseEntity<>(newItem, headers, HttpStatus.CREATED);
	}
	
	/**
	 * Allows the subclass to manipulate the item before returning it
	 * to the caller.
	 * 
	 * @param item is the item to modify
	 * @param id is the id of the item
	 * @return T newly modified item.
	 */
	protected abstract T setupItem(T item, int id);

	/**
	 * Update item.
	 * 
     * @param item is a json representation of the T object 
	 * @return item object containing the updated item
	 * @throws ResourceNotFoundException when the item is not found
	 */
	@RequestMapping(value = RESOURCE + "/{id}", method = RequestMethod.PUT, consumes = "application/json", 
			produces = "application/json")
	@ResponseBody
	public T update(HttpServletRequest request, HttpServletResponse response, 
			@RequestBody T item) throws ResourceNotFoundException {
		
		//TODO the id in the url is not used
		
		LOGGER.info("[START] Updating {}", item);
		
		// Create the item object with the values passed in.
		int result = dao.update(item);
		
		if (result == 0) {
			throw new ResourceNotFoundException();
		} // end if
		
		LOGGER.info("[END] Updating {}", item);
		return item;
	}
	
	/**
	 * List all of the items.
	 * 
	 * @param sorting is used to indicate which fields to sort on
	 * @return Collection of item objects
	 */
	@RequestMapping(value = RESOURCE, method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Collection<T> list(@RequestParam(value = "sorting", required = false) String sorting) {
		LOGGER.info("[START] Retrieving");
		//TODO implement sorting

		LOGGER.info("[END] Retrieving");
		return dao.getList();
	}

	/**
	 * Gets a specific item based on the id passed in.
	 * 
	 * @param id of the item to retrieve
	 * @return item representing the object retrieved by the id
	 * @throws ResourceNotFoundException when the item is not found
	 */
	@RequestMapping(value = RESOURCE + "/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public T getItem(HttpServletResponse response, @PathVariable("id") int id) throws ResourceNotFoundException {
		LOGGER.info("[START] Retrieving item {}", id);
		final T item = dao.getItem(id);
		
		if (item == null) {
			throw new ResourceNotFoundException();
		} // end if
		
		LOGGER.info("[END] Retrieving item {}", id);
		return item;
	}

	/**
	 * Deletes a item based on the id passed in.
	 * 
	 * @param id of the item to delete
	 * @throws ResourceNotFoundException when the item is not found
	 */
	@RequestMapping(value = RESOURCE + "/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteItem(HttpServletResponse response, @PathVariable("id") int id) throws ResourceNotFoundException {
		LOGGER.info("[START] Deleting item {}", id);
		
		int result = dao.delete(id);
		
		if (result == 0) {
			throw new ResourceNotFoundException();
		} // end if
		
		LOGGER.info("[END] Deleting item {}", id);
	}
}