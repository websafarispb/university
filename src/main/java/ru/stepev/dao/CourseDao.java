package ru.stepev.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.stepev.dao.rowmapper.CourseRowMapper;
import ru.stepev.model.Course;

@Component
public class CourseDao {

	private static final String CREATE_COURSE_QUERY = "INSERT INTO courses (course_name, course_description) values (?, ?)";
	private static final String UPDATE_COURSE_BY_ID = "UPDATE courses SET course_name = ?, course_description = ? WHERE course_id = ?";
	private static final String GET_ALL = "SELECT * FROM courses";
	private static final String FIND_COURSE_BY_ID = "SELECT * FROM courses WHERE course_id = ?";
	private static final String DELETE_COURSE_BY_ID = "DELETE FROM courses WHERE course_id = ?";
	private static final String GET_COURSE_ID_BY_NAME = "SELECT course_id FROM courses WHERE course_name = ?";

	@Autowired
	private CourseRowMapper courseRowMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public CourseDao(JdbcTemplate jdbcTemplate, CourseRowMapper courseRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.courseRowMapper = courseRowMapper;
	}

	public void create(Course course) {
		jdbcTemplate.update(CREATE_COURSE_QUERY, course.getName(), course.getDescription());
		course.setId(jdbcTemplate.queryForObject(GET_COURSE_ID_BY_NAME, Integer.class, course.getName()));
	}

	public void update(Course course, int courseId) {
		jdbcTemplate.update(UPDATE_COURSE_BY_ID, course.getName(), course.getDescription(), courseId);
	}

	public void delete(int courseId) {
		jdbcTemplate.update(DELETE_COURSE_BY_ID,  courseId);
	}

	public Course findById(int courseId) {
		return jdbcTemplate.queryForObject(FIND_COURSE_BY_ID, courseRowMapper, courseId);
	}

	public List<Course> findAllCourses() {
		return this.jdbcTemplate.query(GET_ALL, courseRowMapper);
	}
}
