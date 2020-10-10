package ru.stepev.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.stepev.model.Course;

@Component
public class CourseRowMapper implements RowMapper<Course> {

	public Course mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		return new Course(resultSet.getInt("id"), resultSet.getString("course_name"),
				resultSet.getString("course_description"));
	}
}
