package com.activedevsolutions.cloud.aclservice.controller;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.activedevsolutions.cloud.aclservice.dao.PermissionDao;
import com.activedevsolutions.cloud.aclservice.model.Permission;

/**
 * This controller exposes endpoints that would specifically be used to
 * check permissions against a specific resource such as a user or group.
 * 
 * This could easily just be part of the PermissionController as well.
 *
 */
@RestController
@RequestMapping("/v1.0/security")
public class SecurityController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityController.class);
	
	@Autowired
	PermissionDao permissionDao;
	
	/**
	 * List all of the items.
	 * 
	 * @param sorting is used to indicate which fields to sort on
	 * @return Collection of item objects
	 */
	@RequestMapping(value = "/users/{id}", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public Collection<Permission> listByUser(@PathVariable("id") String id) {
		LOGGER.info("[START] Retrieving permissions for {}", id);
		List<Permission> permissions = permissionDao.getPermissionByUser(id); 
		LOGGER.info("[END] Retrieving permissions for {}", id);
		
		return permissions;
	}
	
	//TODO Expose permission by groups in case people don't want to have users in their rbac
}