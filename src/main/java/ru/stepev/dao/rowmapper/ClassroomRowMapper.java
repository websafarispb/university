package ru.stepev.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.stepev.model.Classroom;

@Component
public class ClassroomRowMapper implements RowMapper<Classroom> {

	@Override
	public Classroom mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Classroom classroom = new Classroom(resultSet.getInt("classroom_id"), resultSet.getString("classroom_address"),
				resultSet.getInt("classroom_capacity"));
		return classroom;
	}
}
