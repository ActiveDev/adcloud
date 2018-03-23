package com.activedevsolutions.cloud.aclservice.dao;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.activedevsolutions.cloud.aclservice.model.Group;
import com.activedevsolutions.cloud.aclservice.model.Permission;
import com.activedevsolutions.cloud.aclservice.model.Role;

@SpringBootTest
@PropertySource("bootstrap.yml")
@RunWith(SpringJUnit4ClassRunner.class)
public class DaoTest {
	@Autowired
	private PermissionDao permissionDao;
	
	@Autowired
	private RoleDao roleDao;
	
	@Autowired
	private GroupDao groupDao;
	
	@Test
	public void testGroup() {
		setupPermission();
		List<Permission> permissions = permissionDao.getList();
		setupRole(permissions);
		List<Role> roles = roleDao.getList();
		Group group = setupGroup(permissions, roles);
		deleteGroup(group);

		for (Role role : roles) {
			deleteRole(role);
		}
		
		for (Permission permission : permissions) {
			deletePermission(permission);
		}
	}
	
	private Group setupGroup(List<Permission> permissions, List<Role> roles) {
		// Create
		Group object = new Group();
		object.setId(0);
		object.setName("group1");
		object.setDescription("desc1");
		int result = groupDao.create(object);
		assertTrue(result > 0);
		object.setId(result);
		
		// Add permission
		groupDao.addChildren(object.getId(), roles);
		
		// Get Item
		Group retrievedObject = groupDao.getItem(result);
		assertTrue(retrievedObject!=null);
		assertTrue(retrievedObject.getId() == object.getId());
		assertTrue(retrievedObject.getName().equals(object.getName()));
		assertTrue(!retrievedObject.getRoles().isEmpty());
		
		// Get List
		List<Group> groups = groupDao.getList();
		assertTrue(groups!=null);
		assertTrue(!groups.isEmpty());
		
		// Update
		retrievedObject.setDescription("desc2");
		result = groupDao.update(retrievedObject);
		assertTrue(result == 1);
		Group updatedObject = groupDao.getItem(retrievedObject.getId());
		assertTrue(updatedObject.equals(retrievedObject));

		return updatedObject;
	}
	
	private void deleteGroup(Group group) {	
		// Delete
		int result = groupDao.delete(group.getId());
		assertTrue(result > 0);
		Group retrievedObject = groupDao.getItem(group.getId());
		assertTrue(retrievedObject==null);
	}
	
	@Test
	public void testRole() {
		setupPermission();
		List<Permission> permissions = permissionDao.getList();
		Role role = setupRole(permissions);
		deleteRole(role);
		
		for (Permission permission : permissions) {
			deletePermission(permission);
		}
	}

	private Role setupRole(List<Permission> permissions) {
		// Create
		Role object = new Role();
		object.setId(0);
		object.setName("role1");
		object.setDescription("desc1");
		int result = roleDao.create(object);
		assertTrue(result > 0);
		object.setId(result);
		
		// Add permission
		roleDao.addChildren(object.getId(), permissions);
		
		// Get Item
		Role retrievedObject = roleDao.getItem(result);
		assertTrue(retrievedObject!=null);
		assertTrue(retrievedObject.getId() == object.getId());
		assertTrue(retrievedObject.getName().equals(object.getName()));
		assertTrue(!retrievedObject.getPermissions().isEmpty());
		
		// Get List
		List<Role> roles = roleDao.getList();
		assertTrue(roles!=null);
		assertTrue(!roles.isEmpty());
		
		// Update
		retrievedObject.setDescription("desc2");
		result = roleDao.update(retrievedObject);
		assertTrue(result == 1);
		Role updatedObject = roleDao.getItem(retrievedObject.getId());
		assertTrue(updatedObject.equals(retrievedObject));

		return updatedObject;
	}

	private void deleteRole(Role role) {	
		// Delete
		int result = roleDao.delete(role.getId());
		assertTrue(result > 0);
		Role retrievedObject = roleDao.getItem(role.getId());
		assertTrue(retrievedObject==null);
	}
	
	@Test
	public void testPermission() {
		Permission updatedObject = setupPermission();
		deletePermission(updatedObject);
	}
	
	private Permission setupPermission() {
		// Create
		Permission object = new Permission();
		object.setId(0);
		object.setName("name1");
		object.setEndpoint("endpoint1");
		int result = permissionDao.create(object);
		assertTrue(result > 0);
		object.setId(result);
		
		// Get Item
		Permission retrievedObject = permissionDao.getItem(result);
		assertTrue(retrievedObject!=null);
		assertTrue(retrievedObject.equals(object));
		
		// Get List
		List<Permission> permissions = permissionDao.getList();
		assertTrue(permissions!=null);
		assertTrue(!permissions.isEmpty());
		
		// Update
		retrievedObject.setEndpoint("endpoint2");
		result = permissionDao.update(retrievedObject);
		assertTrue(result == 1);
		Permission updatedObject = permissionDao.getItem(retrievedObject.getId());
		assertTrue(updatedObject.equals(retrievedObject));

		return updatedObject;
	}

	private void deletePermission(Permission permission) {
		// Delete
		int result = permissionDao.delete(permission.getId());
		assertTrue(result > 0);
		Permission retrievedObject = permissionDao.getItem(permission.getId());
		assertTrue(retrievedObject==null);
	}
}
