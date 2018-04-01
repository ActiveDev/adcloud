package com.activedevsolutions.cloud.templateservice;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.activedevsolutions.cloud.core.controller.AbstractController;
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
@RequestMapping("/v1.0/items")
public class TemplateController extends AbstractController<Item> {	
	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void setupItem(Item item, int id) {
		item.setId(id);
	}
}