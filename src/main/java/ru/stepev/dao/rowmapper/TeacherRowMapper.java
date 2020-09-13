package ru.stepev.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.stepev.model.Teacher;

@Component
public class TeacherRowMapper implements RowMapper<Teacher> {

	@Override
	public Teacher mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Teacher teacher = new Teacher(resultSet.getInt("teacher_id"), resultSet.getInt("personal_number"),
				resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("birthday"),
				resultSet.getString("email"), resultSet.getString("gender"), resultSet.getString("address"));
		return teacher;
	}
}
