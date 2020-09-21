package ru.stepev.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.stepev.dao.CourseDao;
import ru.stepev.dao.LectureDao;
import ru.stepev.dao.TeacherDao;
import ru.stepev.model.Teacher;

@Component
public class TeacherRowMapper implements RowMapper<Teacher> {
	
	@Autowired
	public CourseDao courseDao;

	@Override
	public Teacher mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		return new Teacher(resultSet.getInt("id"), resultSet.getInt("personal_number"),
				resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("birthday"),
				resultSet.getString("email"), resultSet.getString("gender"), resultSet.getString("address"),
				courseDao.findAllCoursesForTeacherById(resultSet.getInt("id")));
	}
}
