package com.activedevsolutions.cloud.aclservice.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.activedevsolutions.cloud.aclservice.model.Permission;

/**
 * Provides the data access to the Permission resource.
 *
 */
public class PermissionDao extends AbstractDao<Permission> {
	private static final Logger LOGGER = LoggerFactory.getLogger(PermissionDao.class);
	
	/**
	 * {@inheritDoc}
	 */
	public PermissionDao(JdbcTemplate template, NamedParameterJdbcTemplate namedTemplate) {
		super(template, namedTemplate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createQuery(Permission item) {
		jdbcTemplate.update("INSERT INTO sec_permission (name, endpoint) VALUES (?, ?)", 
				item.getName(), item.getEndpoint());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int updateQuery(Permission item) {
		return jdbcTemplate.update("UPDATE sec_permission SET name = ?, endpoint = ? WHERE id = ?;", 
				item.getName(), item.getEndpoint(), item.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int deleteQuery(int id) {
		return jdbcTemplate.update("DELETE FROM sec_permission WHERE id = ?;", id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<Map<String, Object>> getListQuery() {
		return jdbcTemplate.queryForList("SELECT * FROM sec_permission");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<Map<String, Object>> getItemQuery(int id) {
		return jdbcTemplate.queryForList("SELECT * FROM sec_permission WHERE id = ?", id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Permission assembleItem(Map<String, Object> row) {
		final Permission permission = new Permission();
		permission.setId((int)row.get(ATTRIBUTE_ID));
		permission.setName((String)row.get(ATTRIBUTE_NAME));
		permission.setEndpoint((String)row.get(ATTRIBUTE_ENDPOINT));

		return permission;
	}

	/**
	 * Not implemented as this resource has no children.
	 */
	@Override
	public int addChildren(int id, List<?> children) {
		return 0;
	}

	/**
	 * Not implemented as this resource has no children.
	 */
	@Override
	public int deleteChildren(int id) {
		return 0;
	}
	
	/**
	 * Gets the resource.
	 * 
	 * @param id is the role id
	 * @return List<Permission> is a list of permissions for the role
	 */
	public List<Permission> getPermissionByRole(int id) {
		LOGGER.debug("[START] Get permissions by role {}", id);
		
		List<Permission> items = null;		
		List<Map<String, Object>> results = jdbcTemplate.queryForList(
				"SELECT a.* FROM sec_permission a, sec_role_permission b " +
				"WHERE  a.id = b.permission_id " +
				"AND    b.role_id = ?", id);
		items = parse(results);
		
		LOGGER.debug("[END] Get permissions by role {}", id);
		
		return items;
	}
	
	/**
	 * Gets the resource.
	 * 
	 * @param id is the user id
	 * @return List<Permission> is a list of permissions for the role
	 */
	public List<Permission> getPermissionByUser(String userId) {
		LOGGER.debug("[START] Get permissions for user {}", userId);
		
		List<Permission> items = null;		
		List<Map<String, Object>> results = jdbcTemplate.queryForList(
				"SELECT sp.id, sp.name, sp.endpoint " +
				"FROM   sec_user su, sec_user_group sug,  sec_group_role sgr,  sec_role_permission srp, sec_permission sp " +
				"WHERE  su.user_id = ? " +
				"AND    su.id = sug.user_id " +
				"AND    sug.group_id = sgr.group_id " +
				"AND    sgr.role_id = srp.role_id " +
				"AND    srp.permission_id = sp.id;", userId);
		items = parse(results);
		
		LOGGER.debug("[END] Get permissions for user {}", userId);
		
		return items;
	}	
}
