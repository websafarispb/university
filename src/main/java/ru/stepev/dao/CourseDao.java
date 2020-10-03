package ru.stepev.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ru.stepev.dao.rowmapper.CourseRowMapper;
import ru.stepev.exceptions.ObjectNotFoundException;
import ru.stepev.model.Course;

@Component
public class CourseDao {

	private static final String CREATE_COURSE_QUERY = "INSERT INTO courses (course_name, course_description) values (?, ?)";
	private static final String UPDATE_COURSE_BY_ID = "UPDATE courses SET course_name = ?, course_description = ? WHERE id = ?";
	private static final String GET_ALL = "SELECT * FROM courses";
	private static final String FIND_COURSE_BY_ID = "SELECT * FROM courses WHERE id = ?";
	private static final String DELETE_COURSE_BY_ID = "DELETE FROM courses WHERE id = ?";
	private static final String FIND_ALL_COURSE_BY_TEACHER_ID = "SELECT * FROM "
			+ "courses INNER JOIN teachers_courses ON  courses.id = teachers_courses.course_id WHERE teachers_courses.teacher_id = ?";
	private static final String FIND_ALL_COURSE_BY_STUDENT_ID = "SELECT * FROM "
			+ "courses INNER JOIN students_courses ON  courses.id = students_courses.course_id WHERE students_courses.student_id = ?";

	private CourseRowMapper courseRowMapper;
	private JdbcTemplate jdbcTemplate;

	public CourseDao(JdbcTemplate jdbcTemplate, CourseRowMapper courseRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.courseRowMapper = courseRowMapper;
	}

	public void create(Course course) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(CREATE_COURSE_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, course.getName());
			statement.setString(2, course.getDescription());
			return statement;
		}, keyHolder);
		course.setId((int) keyHolder.getKeys().get("id"));
	}

	public void update(Course course, int courseId) {
		jdbcTemplate.update(UPDATE_COURSE_BY_ID, course.getName(), course.getDescription(), courseId);
	}

	public void delete(int courseId) {
		jdbcTemplate.update(DELETE_COURSE_BY_ID, courseId);
	}

	public Course findById(int courseId) {
		try {
			return jdbcTemplate.queryForObject(FIND_COURSE_BY_ID, courseRowMapper, courseId);
		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException(String.format("Course with Id = %d not found !!!", courseId), e);
		}
	}

	public List<Course> findAll() {
		return jdbcTemplate.query(GET_ALL, courseRowMapper);
	}

	public List<Course> findByTeacherId(int teacherId) {
		return jdbcTemplate.query(FIND_ALL_COURSE_BY_TEACHER_ID, courseRowMapper, teacherId);
	}

	public List<Course> findByStudentId(int studentId) {
		return jdbcTemplate.query(FIND_ALL_COURSE_BY_STUDENT_ID, courseRowMapper, studentId);
	}
}
