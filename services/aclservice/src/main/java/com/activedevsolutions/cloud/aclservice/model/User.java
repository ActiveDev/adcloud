package com.activedevsolutions.cloud.aclservice.model;

import java.util.List;
import java.util.Objects;

/**
 * Represents a user. 
 *
 */
public class User {
	private int id;
	private String userId;
	private String firstName;
	private String lastName;
	
	private List<Group> groups;
	
	// Getters and Setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public List<Group> getGroups() {
		return groups;
	}
	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Email: ");
		builder.append(userId);
		builder.append("\tFirstName: ");
		builder.append(firstName);
		builder.append("\tLastName: ");
		builder.append(lastName);
		
		return builder.toString();
	}
	
    @Override
    public boolean equals(Object otherObject) {

        if (otherObject == this) return true;
        if (!(otherObject instanceof User)) {
            return false;
        }
        User castObject = (User) otherObject;
        return id == castObject.id &&
                Objects.equals(userId, castObject.userId) &&
                Objects.equals(firstName, castObject.firstName) &&
                Objects.equals(lastName, castObject.lastName) &&
                Objects.equals(groups, castObject.groups);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, userId, firstName, lastName, groups);
    }
}
