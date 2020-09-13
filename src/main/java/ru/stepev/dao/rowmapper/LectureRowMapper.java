package ru.stepev.dao.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import ru.stepev.model.Classroom;
import ru.stepev.model.Course;
import ru.stepev.model.Group;
import ru.stepev.model.Lecture;
import ru.stepev.model.Teacher;

@Component
public class LectureRowMapper implements RowMapper<Lecture> {

	private static final String FILL_LECTURE = "SELECT * FROM courses, classrooms, groups, teachers  WHERE courses.course_id = ? and classrooms.classroom_id = ? and groups.group_id = ? and teachers.teacher_id = ?";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public LectureRowMapper(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public Lecture mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		
		Object[] objects = new Object[] { resultSet.getInt("course_id"), resultSet.getInt("classroom_id"),
				resultSet.getInt("group_id"), resultSet.getInt("teacher_id") };
		Lecture lecture = jdbcTemplate.queryForObject(FILL_LECTURE, objects, new RowMapper<Lecture>() {
			public Lecture mapRow(ResultSet rs, int arg) throws SQLException {

				Course course = new Course(rs.getInt("course_id"), rs.getString("course_name"),
						rs.getString("course_description"));

				Classroom classroom = new Classroom(resultSet.getInt("classroom_id"), rs.getString("classroom_address"),
						rs.getInt("classroom_capacity"));
				Group group = new Group(resultSet.getInt("group_id"), rs.getString("group_name"));

				Teacher teacher = new Teacher(rs.getInt("teacher_id"), rs.getInt("personal_number"),
						rs.getString("first_name"), rs.getString("last_name"), rs.getString("birthday"),
						rs.getString("email"), rs.getString("gender"), rs.getString("address"));

				Lecture lecture = new Lecture(course, classroom, group, teacher);
				return lecture;
			}
		});
		lecture.setId(resultSet.getInt("lecture_id"));
		lecture.setDate(LocalDate.parse(resultSet.getString("local_date")));
		lecture.setTime(LocalTime.parse(resultSet.getString("local_time")));
		return lecture;
	}
}
