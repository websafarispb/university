package ru.stepev.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.stepev.dao.StudentDao;
import ru.stepev.model.Group;

@Component
public class GroupRowMapper implements RowMapper<Group> {
	
	private StudentDao studentDao;
	
	public GroupRowMapper(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	@Override
	public Group mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		return new Group(resultSet.getInt("id"), resultSet.getString("group_name"), studentDao.findByGroupId(resultSet.getInt("id")));
	}
}
