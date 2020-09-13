package ru.stepev.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.stepev.model.Student;

@Component
public class StudentRowMapper implements RowMapper<Student> {

	@Override
	public Student mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Student student = new Student(resultSet.getInt("student_id"), resultSet.getInt("personal_number"),
				resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("birthday"),
				resultSet.getString("email"), resultSet.getString("gender"), resultSet.getString("address"));
		return student;
	}
}
