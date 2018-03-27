package com.activedevsolutions.cloud.aclservice.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.activedevsolutions.cloud.aclservice.model.Permission;

/**
 * Exposes REST endpoints for the Permission resource.
 *
 */
@RestController
@RequestMapping("/v1.0/permissions")
public class PermissionController extends AbstractController<Permission> {	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Permission setupItem(Permission item, int id) {
		//TODO Create immutable object
		item.setId(id);
		return item;
	}
	
	@Override
	protected String getMapping() {
		return "/v1.0/permissions";
	}
}