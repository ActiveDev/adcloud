package com.activedevsolutions.cloud.aclservice.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import com.activedevsolutions.cloud.aclservice.model.Group;
import com.activedevsolutions.cloud.aclservice.model.User;

/**
 * Provides the data access to the Permission resource.
 *
 */
public class UserDao extends AbstractDao<User> {
	@Autowired
	private GroupDao groupDao;
	
	/**
	 * {@inheritDoc}
	 */
	public UserDao(JdbcTemplate template, NamedParameterJdbcTemplate namedTemplate) {
		super(template, namedTemplate);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void createQuery(User item) {
		jdbcTemplate.update("INSERT INTO sec_user (email, first_name, last_name) VALUES (?, ?, ?)", 
				item.getEmail(), item.getFirstName(), item.getLastName());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int updateQuery(User item) {
		return jdbcTemplate.update("UPDATE sec_user SET email = ?, first_name = ?, last_name = ? WHERE id = ?;", 
				item.getEmail(), item.getFirstName(), item.getLastName(), item.getId());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected int deleteQuery(int id) {
		return jdbcTemplate.update("DELETE sec_user WHERE id = ?;", id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<Map<String, Object>> getListQuery() {
		return jdbcTemplate.queryForList("SELECT * FROM sec_user");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected List<Map<String, Object>> getItemQuery(int id) {
		return jdbcTemplate.queryForList("SELECT * FROM sec_user WHERE id = ?", id);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected User assembleItem(Map<String, Object> row) {
		final User user = new User();
		user.setId((int)row.get(ATTRIBUTE_ID));
		user.setEmail((String)row.get(ATTRIBUTE_EMAIL));
		user.setFirstName((String)row.get(ATTRIBUTE_FIRST_NAME));
		user.setLastName((String)row.get(ATTRIBUTE_LAST_NAME));

		List<Group> groups = groupDao.getGroupByUser(user.getId());
		user.setGroups(groups);
		
		return user;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int addChildren(int id, List<?> children) {
		int result = 0;
		for (Object child : children) {
			Group item = (Group) child; 
			result += jdbcTemplate.update("INSERT INTO sec_user_group (user_id, group_id) VALUES (?, ?);", 
					id, item.getId());
		} // end for
		
		return result;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int deleteChildren(int id) {
		return jdbcTemplate.update("DELETE sec_user_group WHERE user_id = ?;", id);
	}
}
