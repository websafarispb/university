package ru.stepev.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.stepev.model.Group;

@Component
public class GroupRowMapper implements RowMapper<Group> {

	@Override
	public Group mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Group group = new Group(resultSet.getInt("group_id"), resultSet.getString("group_name"));
		return group;
	}
}
