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
import com.activedevsolutions.cloud.aclservice.model.Permission;
import com.activedevsolutions.cloud.aclservice.model.Role;

/**
 * Exposes REST endpoints for the Role resource.
 *
 */
@RestController
@RequestMapping("/v1.0/roles")
public class RoleController extends AbstractController<Role> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleController.class);
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setupItem(Role item, int id) {
		item.setId(id);
	}
	
	@Override
	protected String getMapping() {
		return "/v1.0/roles";
	}

	
	/**
	 * Adds permissions to a role.
	 * 
	 * @param id is the role id
	 * @return body contains a json representation of List<Permissions>
	 * @throws ResourceNotFoundException 
	 */
	@RequestMapping(value = "/{id}/permissions", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	@ResponseBody
	public ResponseEntity<Role> addChildren(@PathVariable("id") int id, 
			@RequestBody List<Permission> permissions, UriComponentsBuilder ucb) throws ResourceNotFoundException {
 		LOGGER.info("[START] Adding {}", permissions);
		
		// Get the role first
		Role role = dao.getItem(id);
		if (role == null) {
			throw new ResourceNotFoundException();
		} // end if

		dao.addChildren(id, permissions);
		role.setPermissions(permissions);
		
		// Set the location for the new object
		HttpHeaders headers = new HttpHeaders();
		URI locationUri = ucb.path(getMapping() + "/" +  id + "/permissions/").build().toUri();
		headers.setLocation(locationUri);
		
		LOGGER.info("[END] Adding {}", permissions);
		return new ResponseEntity<>(role, headers, HttpStatus.CREATED);
	}
	
	/**
	 * Deletes permissions from a role.
	 * 
	 * @param id is the role id
	 * @throws ResourceNotFoundException 
	 */
	@RequestMapping(value = "/{id}/permissions", method = RequestMethod.DELETE, consumes = "application/json", produces = "application/json")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void removeChildren(@PathVariable("id") int id) throws ResourceNotFoundException {
 		LOGGER.info("[START] Deleting permissions for {}", id);
		
		// Delete children
		int result = dao.deleteChildren(id);
		
		if (result == 0) {
			throw new ResourceNotFoundException();
		} // end if
				
		LOGGER.info("[END] Deleting permissions for {}", id);
	}
}
