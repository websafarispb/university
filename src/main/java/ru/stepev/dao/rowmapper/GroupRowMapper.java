package ru.stepev.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.stepev.dao.StudentDao;
import ru.stepev.model.Group;

@Component
public class GroupRowMapper implements RowMapper<Group> {
	
	@Autowired
	public StudentDao studentDao;

	@Override
	public Group mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		return new Group(resultSet.getInt("id"), resultSet.getString("group_name"), studentDao.findStudentsByGroupId(resultSet.getInt("id")));
	}
}
