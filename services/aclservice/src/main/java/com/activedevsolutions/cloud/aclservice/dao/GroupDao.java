package com.activedevsolutions.cloud.aclservice.dao;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.activedevsolutions.cloud.core.security.model.Group;
import com.activedevsolutions.cloud.core.security.model.Role;

/**
 * Provides the data access to the Group resource.
 *
 */
public class GroupDao extends JdbcDao<Group> {
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupDao.class);
	
	@Autowired
	private RoleDao roleDao;
	
	/**
	 * {@inheritDoc}
	 */
	public GroupDao(JdbcTemplate template, NamedParameterJdbcTemplate namedTemplate) {
		super(template, namedTemplate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int createQuery(Group item) {
		return jdbcTemplate.update("INSERT INTO sec_group (name, description) VALUES (?, ?)", 
				item.getName(), item.getDescription());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int updateQuery(Group item) {
		return jdbcTemplate.update("UPDATE sec_group SET name = ?, description = ? WHERE id = ?;", 
				item.getName(), item.getDescription(), item.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int deleteQuery(int id) {
		return jdbcTemplate.update("DELETE FROM sec_group WHERE id = ?;", id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<Map<String, Object>> getListQuery() {
		return jdbcTemplate.queryForList("SELECT * FROM sec_group");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<Map<String, Object>> getItemQuery(int id) {
		return jdbcTemplate.queryForList("SELECT * FROM sec_group WHERE id = ?", id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Group assembleItem(Map<String, Object> row) {
		final Group group = new Group();
		group.setId((int)row.get(ATTRIBUTE_ID));
		group.setName((String)row.get(ATTRIBUTE_NAME));
		group.setDescription((String)row.get(ATTRIBUTE_DESCRIPTION));

		List<Role> roles = roleDao.getRoleByGroup(group.getId());
		group.setRoles(roles);
		
		return group;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int addChildren(int id, List<?> children) {
		int result = 0;
		for (Object child : children) {
			Role item = (Role) child; 
			result += jdbcTemplate.update("INSERT INTO sec_group_role (group_id, role_id) VALUES (?, ?);", 
					id, item.getId());
		} // end for
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteChildren(int id) {
		return jdbcTemplate.update("DELETE FROM sec_group_role WHERE group_id = ?;", id);
	}
	
	/**
	 * Gets the resource.
	 * 
	 * @param id is the user id
	 * @return List<Group> is a list of groups for the user
	 */
	public List<Group> getGroupByUser(int id) {
		LOGGER.debug("[START] Get groups by user {}", id);
		
		List<Group> items = null;		
		List<Map<String, Object>> results = jdbcTemplate.queryForList(
				"SELECT a.* FROM sec_group a, sec_user_group b " +
				"WHERE  a.id = b.group_id " +
				"AND    b.user_id = ?", id);
		items = parse(results);
		
		LOGGER.debug("[END] Get groups by user {}", id);
		
		return items;
	}
}
