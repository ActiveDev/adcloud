package com.activedevsolutions.cloud.aclservice.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.activedevsolutions.cloud.core.controller.AbstractController;
import com.activedevsolutions.cloud.core.exception.ResourceNotFoundException;
import com.activedevsolutions.cloud.core.security.model.Group;
import com.activedevsolutions.cloud.core.security.model.User;

/**
 * Exposes REST endpoints for the Role resource.
 *
 */
@RestController
@RequestMapping("/v1.0/users")
public class UserController extends AbstractController<User> {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setupItem(User item, int id) {
		item.setId(id);
	}
	
	@Override
	protected String getMapping() {
		return "/v1.0/users";
	}
	
	/**
	 * Adds groups to a user.
	 * 
	 * @param id is the user id
	 * @return body contains a json representation of List<Groups>
	 * @throws ResourceNotFoundException 
	 */
	@RequestMapping(value = "/{id}/groups", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<User> addChildren(@PathVariable("id") int id, 
			@RequestBody List<Group> groups, UriComponentsBuilder ucb) throws ResourceNotFoundException {
 		LOGGER.info("[START] Adding {}", groups);
		// Create the item object with the values passed in.
		
		// Get the group first
		User user = dao.getItem(id);
		if (user == null) {
			throw new ResourceNotFoundException();
		} // end if

		dao.addChildren(id, groups);
		user.setGroups(groups);
		
		// Set the location for the new object
		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path(getMapping() + "/" +  id + "/groups/").build().toUri();
		headers.setLocation(locationUri);
		
		LOGGER.info("[END] Adding {}", groups);
		return new ResponseEntity<>(user, headers, HttpStatus.CREATED);
	}
	
	/**
	 * Deletes groups from a user.
	 * 
	 * @param id is the user id
	 * @throws ResourceNotFoundException 
	 */
	@RequestMapping(value = "/{id}/groups", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeChildren(@PathVariable("id") int id) throws ResourceNotFoundException {
 		LOGGER.info("[START] Deleting groups for {}", id);
		
		// Delete children
		int result = dao.deleteChildren(id);
		
		if (result == 0) {
			throw new ResourceNotFoundException();
		} // end if
				
		LOGGER.info("[END] Deleting groups for {}", id);
	}
}
