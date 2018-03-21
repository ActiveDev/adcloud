package com.activedevsolutions.rbac.autoconfigure;

/**
 * Defines the types of RBAC Authorization methods.
 *
 */
public enum RBACAuthMethod {
	NONE ("none"),
	FILTER ("filter"),
	GRANTED_AUTH ("granted-auth");

    private final String name;       

    private RBACAuthMethod(String value) {
        name = value;
    }

    public String getName() {
    	return name;
    }
}
