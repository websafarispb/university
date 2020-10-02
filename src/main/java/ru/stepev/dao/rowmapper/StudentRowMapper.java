package ru.stepev.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.stepev.dao.CourseDao;
import ru.stepev.model.Student;

@Component
public class StudentRowMapper implements RowMapper<Student> {

	private CourseDao courseDao;
	
	public StudentRowMapper(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	@Override
	public Student mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Student student = new Student();
		student.setId(resultSet.getInt("id"));
		student.setPersonalNumber(resultSet.getInt("personal_number"));
		student.setFirstName(resultSet.getString("first_name"));
		student.setLastName(resultSet.getString("last_name"));
		student.setBirthday(LocalDate.parse(resultSet.getString("birthday")));
		student.setEmail(resultSet.getString("email"));
		student.setGender(resultSet.getString("gender"));
		student.setAddres(resultSet.getString("address"));
		student.setCourses(courseDao.findByStudentId(resultSet.getInt("id")));
		return student;
	}
}
