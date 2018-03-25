package com.activedevsolutions.cloud.aclservice.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.activedevsolutions.cloud.aclservice.model.Permission;
import com.activedevsolutions.cloud.aclservice.model.Role;

/**
 * Provides the data access to the Permission resource.
 *
 */
public class RoleDao extends AbstractDao<Role> {
	private static final Logger LOGGER = LoggerFactory.getLogger(RoleDao.class);
	
	@Autowired
	private PermissionDao permissionDao;
	
	/**
	 * {@inheritDoc}
	 */
	public RoleDao(JdbcTemplate template, NamedParameterJdbcTemplate namedTemplate) {
		super(template, namedTemplate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createQuery(Role item) {
		jdbcTemplate.update("INSERT INTO sec_role (name, description) VALUES (?, ?)", 
				item.getName(), item.getDescription());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int updateQuery(Role item) {
		return jdbcTemplate.update("UPDATE sec_role SET name = ?, description = ? WHERE id = ?;", 
				item.getName(), item.getDescription(), item.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int deleteQuery(int id) {
		return jdbcTemplate.update("DELETE FROM sec_role WHERE id = ?;", id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<Map<String, Object>> getListQuery() {
		return jdbcTemplate.queryForList("SELECT * FROM sec_role");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<Map<String, Object>> getItemQuery(int id) {
		return jdbcTemplate.queryForList("SELECT * FROM sec_role WHERE id = ?", id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Role assembleItem(Map<String, Object> row) {
		final Role role = new Role();
		role.setId((int)row.get(ATTRIBUTE_ID));
		role.setName((String)row.get(ATTRIBUTE_NAME));
		role.setDescription((String)row.get(ATTRIBUTE_DESCRIPTION));

		List<Permission> permissions = permissionDao.getPermissionByRole(role.getId());
		role.setPermissions(permissions);
		
		return role;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int addChildren(int id, List<?> children) {
		int result = 0;
		for (Object child : children) {
			Permission item = (Permission) child; 
			result += jdbcTemplate.update("INSERT INTO sec_role_permission (role_id, permission_id) VALUES (?, ?);", 
					id, item.getId());
		} // end for
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteChildren(int id) {
		return jdbcTemplate.update("DELETE FROM sec_role_permission WHERE role_id = ?;", id);
	}
	
	/**
	 * Gets the resource.
	 * 
	 * @param id is the group id
	 * @return List<Role> is a list of roles for the group
	 */
	public List<Role> getRoleByGroup(int id) {
		LOGGER.debug("[START] Get roles by group {}", id);
		
		List<Role> items = null;		
		List<Map<String, Object>> results = jdbcTemplate.queryForList(
				"SELECT a.* FROM sec_role a, sec_group_role b " +
				"WHERE  a.id = b.role_id " +
				"AND    b.group_id = ?", id);
		items = parse(results);
		
		LOGGER.debug("[END] Get roles by group {}", id);
		
		return items;
	}
}
