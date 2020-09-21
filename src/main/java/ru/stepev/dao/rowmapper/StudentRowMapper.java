package ru.stepev.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.stepev.dao.CourseDao;
import ru.stepev.model.Student;

@Component
public class StudentRowMapper implements RowMapper<Student> {

	@Autowired
	public CourseDao courseDao;
	@Override
	public Student mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		return new Student(resultSet.getInt("id"), resultSet.getInt("personal_number"),
				resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("birthday"),
				resultSet.getString("email"), resultSet.getString("gender"), resultSet.getString("address"),
				courseDao.findAllCoursesForStudentById(resultSet.getInt("id")));
	}
}
