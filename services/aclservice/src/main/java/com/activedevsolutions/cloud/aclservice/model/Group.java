package com.activedevsolutions.cloud.aclservice.model;

import java.util.List;
import java.util.Objects;

/**
 * Group of roles.
 */
public class Group {
	private int id;
	private String name;
	private String description;
	private List<Role> roles;

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
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("\tName: ");
		builder.append(name);
		builder.append("\tDescription: ");
		builder.append(description);
		
		return builder.toString();
	}
	
    @Override
    public boolean equals(Object otherObject) {

        if (otherObject == this) return true;
        if (!(otherObject instanceof Group)) {
            return false;
        }
        Group castObject = (Group) otherObject;
        return id == castObject.id &&
                Objects.equals(name, castObject.name) &&
                Objects.equals(description, castObject.description) &&
                Objects.equals(roles, castObject.roles);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, roles);
    }
}
