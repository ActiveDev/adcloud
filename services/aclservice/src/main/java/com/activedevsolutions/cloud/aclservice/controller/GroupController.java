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

import com.activedevsolutions.cloud.aclservice.exception.ResourceNotFoundException;
import com.activedevsolutions.cloud.aclservice.model.Group;
import com.activedevsolutions.cloud.aclservice.model.Role;

/**
 * Exposes REST endpoints for the Group resource.
 *
 */
@RestController
@RequestMapping("/v1.0/groups")
public class GroupController extends AbstractController<Group> {
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setupItem(Group item, int id) {
		item.setId(id);
	}
	
	@Override
	protected String getMapping() {
		return "/v1.0/groups";
	}
	
	/**
	 * Adds roles to a group.
	 * 
	 * @param id is the group id
	 * @return body contains a json representation of List<Roles>
	 * @throws ResourceNotFoundException 
	 */
	@RequestMapping(value = "/{id}/roles", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Group> addChildren(@PathVariable("id") int id, 
			@RequestBody List<Role> roles, UriComponentsBuilder ucb) throws ResourceNotFoundException {
 		LOGGER.info("[START] Adding roles for {}", id);
		
		// Get the group first
		Group group = dao.getItem(id);
		if (group == null) {
			throw new ResourceNotFoundException();
		} // end if

		dao.addChildren(id, roles);
		group.setRoles(roles);
		
		// Set the location for the new object
		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path(getMapping() + "/" +  id + "/roles/").build().toUri();
		headers.setLocation(locationUri);
		
		LOGGER.info("[END] Adding roles for {}", id);
		return new ResponseEntity<>(group, headers, HttpStatus.CREATED);
	}
	
	/**
	 * Deletes roles from a group.
	 * 
	 * @param id is the group id
	 * @throws ResourceNotFoundException 
	 */
	@RequestMapping(value = "/{id}/roles", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeChildren(@PathVariable("id") int id) throws ResourceNotFoundException {
 		LOGGER.info("[START] Deleting roles for {}", id);
		
		// Delete children
		int result = dao.deleteChildren(id);
		
		if (result == 0) {
			throw new ResourceNotFoundException();
		} // end if
				
		LOGGER.info("[END] Deleting roles for {}", id);
	}
}