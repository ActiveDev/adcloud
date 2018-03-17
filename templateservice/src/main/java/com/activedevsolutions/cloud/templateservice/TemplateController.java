package com.activedevsolutions.cloud.templateservice;


import java.net.URI;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.activedevsolutions.cloud.templateservice.exception.ResourceNotFoundException;
import com.activedevsolutions.cloud.templateservice.model.Item;

/**
 * Very simple demonstration of one way to implement a REST controller that
 * follows the REST conventions. This item controller will perform CRUD
 * operations on item objects.
 * 
 * At the moment, it uses one exception handler to return 400 Bad Request.
 * This is because each method will update the http status as it sees fit.
 * The alternative is for each validation to throw a specific exception and have
 * an exception handler that will return the appropriate http status based on the
 * exception class.
 */
@RestController
@RequestMapping("/v1.0")
public class TemplateController {
	// Our in-memory list keyed by the id (
	private Map<String, Item> items = new ConcurrentHashMap<>();
	
	/**
	 * REST Conventions use a POST for adding an object.
	 * This method will add a item object to our in-memory list.
	 * For simplicity, we won't be doing any unique constraints.
	 * 
	 * @param param1 is a parameter
	 * @param param2 is a parameter
	 * @param param3 is a parameter
	 * 
	 * @return item object containing the newly created item
	 */
	@RequestMapping(value = "/items", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	//@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Item> add(
			@RequestParam(value = "param1") String param1,
			@RequestParam(value = "param2") String param2,
			@RequestParam(value = "param3") String param3,
			UriComponentsBuilder ucb) {
 		
		// Create the item object with the values passed in.
		// NOTE, not unique constraints are enforced in this sample code
		final Item item = new Item();
		item.setParam1(param1);
		item.setParam2(param2);
		item.setParam3(param3);
		
		// Let's just fake a generated id so we can send something extra back
		// for demonstration purposes
		final String id = UUID.randomUUID().toString();
		item.setId(id);
		
		// Demo purposes so there is no thread safety here
		items.putIfAbsent(id, item);
		
		// Set the location for the new object
		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path("/v1.0/items/").path(id).build().toUri();
		headers.setLocation(locationUri);
		
		return new ResponseEntity<>(item, headers, HttpStatus.CREATED);
	}

	/**
	 * REST Conventions use a PATCH for partially updating an object.
	 * In this case, we will be updating the price on a particular
	 * item.
	 * 
	 * @param param3 is the value to update the item with
	 * 
	 * @return item object containing the newly created item
	 */
	@RequestMapping(value = "/items/{id}", method = RequestMethod.PATCH, 
			produces = "application/json")
	@ResponseBody
	public Item update(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id, 
			@RequestParam(value = "param3", required = false) String param3) throws ResourceNotFoundException {
 		
		Item item;
		
		// Demo purposes so there is no thread safety here
		if (items.containsKey(id)) {
			item = items.get(id);
			item.setParam3(param3);
		}
		else {
			throw new ResourceNotFoundException();
		} // end if
		
		return item;
	}
	
	/**
	 * List all of the items in our list.
	 * 
	 * @param exchange is used to filter the list to only show ones belonging to a specific exchange
	 * @param sorting is used to indicate which fields to sort on
	 * @return Collection of item objects matching any filters and sorting passed in
	 */
	@RequestMapping(value = "/items", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Collection<Item> list(@RequestParam(value = "exchange", required = false) String exchange,
			@RequestParam(value = "sorting", required = false) String sorting) {

		//TODO implement filtering
		//TODO implement sorting
		
		return items.values();
	}

	/**
	 * Gets a very specific item object our of the in-memory list
	 * based on the id passed in.
	 * 
	 * @param id of the item to retrieve
	 * @return item representing the object retrieved by the id
	 */
	@RequestMapping(value = "/items/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Item getItem(HttpServletResponse response, @PathVariable("id") String id) throws ResourceNotFoundException {
		Item item;
		
		if (items.containsKey(id)) {
			item = items.get(id);
		}
		else {
			throw new ResourceNotFoundException();
		} // end if
		
		return item;
	}

	/**
	 * Deletes a very specific item object our of the in-memory list
	 * based on the id passed in.
	 * 
	 * @param id of the item to retrieve
	 * @return item representing the object retrieved by the id
	 */
	@RequestMapping(value = "/items/{id}", method = RequestMethod.DELETE, produces = "application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteItem(HttpServletResponse response, @PathVariable("id") String id) throws ResourceNotFoundException {		
		// Demo purposes so there is no thread safety here
		if (items.containsKey(id)) {
			items.remove(id);
		}
		else {
			throw new ResourceNotFoundException();
		} // end if
	}
	
	/**
	 * Used to simulate a timeout for playing with Hystrix.
	 * 
	 * @return item is just a dummy object
	 * @throws InterruptedException when the thread sleep fails
	 */
	@RequestMapping(value = "/items/timeout", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Item simulateTimeout() throws InterruptedException {
		Item item = new Item();
		item.setParam1("Should have timed out");
		
		Thread.sleep(10000);
		
		return item;
	}
}