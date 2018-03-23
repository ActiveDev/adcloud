package com.activedevsolutions.cloud.aclservice.model;

import java.util.List;
import java.util.Objects;

/**
* Role bean that holds a list of permissions.
*
*/
public class Role {
	private int id;
	private String name;
	private String description;
	private List<Permission> permissions;
	
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
	public List<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
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

		if (permissions != null) {
			builder.append("\nPermissions");
			builder.append(permissions.toString());
		} // end if

		return builder.toString();
	}
	
    @Override
    public boolean equals(Object otherObject) {

        if (otherObject == this) return true;
        if (!(otherObject instanceof Role)) {
            return false;
        }
        Role castObject = (Role) otherObject;
        return id == castObject.id &&
                Objects.equals(name, castObject.name) &&
                Objects.equals(description, castObject.description) &&
                Objects.equals(permissions, castObject.permissions);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, permissions);
    }
}
