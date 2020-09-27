package ru.stepev.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.stepev.dao.CourseDao;
import ru.stepev.model.Gender;
import ru.stepev.model.Teacher;

@Component
public class TeacherRowMapper implements RowMapper<Teacher> {

	@Autowired
	public CourseDao courseDao;

	@Override
	public Teacher mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		Teacher teacher = new Teacher();
		teacher.setId(resultSet.getInt("id"));
		teacher.setPersonalNumber(resultSet.getInt("personal_number"));
		teacher.setFirstName(resultSet.getString("first_name"));
		teacher.setLastName(resultSet.getString("last_name"));
		teacher.setBirthday(LocalDate.parse(resultSet.getString("birthday")));
		teacher.setEmail(resultSet.getString("email"));
		teacher.setGender(Gender.valueOf(resultSet.getString("gender")));
		teacher.setAddres(resultSet.getString("address"));
		teacher.setCourses(courseDao.findAllForTeacherById(resultSet.getInt("id")));
		return teacher;
	}
}
