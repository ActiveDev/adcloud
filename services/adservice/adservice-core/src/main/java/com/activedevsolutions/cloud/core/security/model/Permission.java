package com.activedevsolutions.cloud.core.security.model;

import java.util.Objects;

/**
 * Permission represents a service endpoint.
 *
 */
public class Permission {
	private int id;
	private String name;
	private String endpoint;
	
	// Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEndpoint() {
		return endpoint;
	}
	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\tName: ");
		builder.append(name);
		builder.append("\tEndpoint: ");
		builder.append(endpoint);
		
		return builder.toString();
	}
	
    @Override
    public boolean equals(Object otherObject) {

        if (otherObject == this) return true;
        if (!(otherObject instanceof Permission)) {
            return false;
        }
        Permission castObject = (Permission) otherObject;
        return id == castObject.id &&
                Objects.equals(name, castObject.name) &&
                Objects.equals(endpoint, castObject.endpoint);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, endpoint);
    }
}

