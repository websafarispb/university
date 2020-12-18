package ru.stepev.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.stepev.dao.CourseDao;
import ru.stepev.dao.jdbc.rowmapper.CourseRowMapper;
import ru.stepev.exception.EntityCouldNotBeenCreatedException;
import ru.stepev.exception.EntityCouldNotBeenDeletedException;
import ru.stepev.exception.EntityCouldNotBeenUpdatedException;
import ru.stepev.model.Course;

@Component
@Slf4j
public class JdbcCourseDao implements CourseDao {

	private static final String CREATE_COURSE_QUERY = "INSERT INTO courses (course_name, course_description) values (?, ?)";
	private static final String UPDATE_COURSE_BY_ID = "UPDATE courses SET course_name = ?, course_description = ? WHERE id = ?";
	private static final String GET_ALL = "SELECT * FROM courses";
	private static final String FIND_COURSE_BY_ID = "SELECT * FROM courses WHERE id = ?";
	private static final String FIND_COURSE_BY_NAME = "SELECT * FROM courses WHERE course_name = ?";
	private static final String DELETE_COURSE_BY_ID = "DELETE FROM courses WHERE id = ?";
	private static final String FIND_ALL_COURSE_BY_TEACHER_ID = "SELECT * FROM "
			+ "courses INNER JOIN teachers_courses ON  courses.id = teachers_courses.course_id WHERE teachers_courses.teacher_id = ?";
	private static final String FIND_ALL_COURSE_BY_STUDENT_ID = "SELECT * FROM "
			+ "courses INNER JOIN students_courses ON  courses.id = students_courses.course_id WHERE students_courses.student_id = ?";
	private static final String FIND_AND_SORT_BY_ID = "SELECT * FROM courses ORDER BY id ASC LIMIT ? OFFSET ?";
	private static final String FIND_AND_SORT_BY_NAME = "SELECT * FROM courses ORDER BY course_name ASC LIMIT ? OFFSET ?";
	private static final String FINF_NUMBER_OF_COURSES = "SELECT COUNT(*) FROM courses";

	private CourseRowMapper courseRowMapper;
	private JdbcTemplate jdbcTemplate;

	public JdbcCourseDao(JdbcTemplate jdbcTemplate, CourseRowMapper courseRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.courseRowMapper = courseRowMapper;
	}

	public void create(Course course) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		if (jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(CREATE_COURSE_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, course.getName());
			statement.setString(2, course.getDescription());
			return statement;
		}, keyHolder) == 0) {
			throw new EntityCouldNotBeenCreatedException(
					String.format("Course with name %s could not been created!!!", course.getName()));
		}
		course.setId((int) keyHolder.getKeys().get("id"));
	}

	public void update(Course course) {
		if (jdbcTemplate.update(UPDATE_COURSE_BY_ID, course.getName(), course.getDescription(), course.getId()) == 0) {
			throw new EntityCouldNotBeenUpdatedException(
					String.format("Course with name %s could not been update!!!", course.getName()));
		}
	}

	public void delete(int courseId) {
		if (jdbcTemplate.update(DELETE_COURSE_BY_ID, courseId) == 0) {
			throw new EntityCouldNotBeenDeletedException(
					String.format("Course with Id %s could not been delete!!!", courseId));
		}
	}

	public Optional<Course> findById(int courseId) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(FIND_COURSE_BY_ID, courseRowMapper, courseId));
		} catch (EmptyResultDataAccessException e) {
			log.warn("Course with id {} was not found", courseId);
			return Optional.empty();
		}
	}

	public List<Course> findAll() {
		log.debug("Finding all courses...");
		return jdbcTemplate.query(GET_ALL, courseRowMapper);
	}

	public List<Course> findByTeacherId(int teacherId) {
		log.debug("Finding course by teacher ID ...");
		return jdbcTemplate.query(FIND_ALL_COURSE_BY_TEACHER_ID, courseRowMapper, teacherId);
	}

	public List<Course> findByStudentId(int studentId) {
		log.debug("Finding course by student ID ...");
		return jdbcTemplate.query(FIND_ALL_COURSE_BY_STUDENT_ID, courseRowMapper, studentId);
	}

	@Override
	public Optional<Course> findByName(String nameOfCourse) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(FIND_COURSE_BY_NAME, courseRowMapper, nameOfCourse));
		} catch (EmptyResultDataAccessException e) {
			log.warn("Course with name {} was not found", nameOfCourse);
			return Optional.empty();
		}
	}

	@Override
	public List<Course> findAndSortByName(int numberOfItems, int offset) {
		log.debug("Finding and sorting courses by name ... ");
		Object[] objects = new Object[] { numberOfItems, offset };
		return jdbcTemplate.query(FIND_AND_SORT_BY_NAME, objects, courseRowMapper);
	}

	@Override
	public List<Course> findAndSortById(int numberOfItems, int offset) {
		log.debug("Finding and sorting courses by id ... ");
		Object[] objects = new Object[] { numberOfItems, offset };
		return jdbcTemplate.query(FIND_AND_SORT_BY_ID, objects, courseRowMapper);
	}

	@Override
	public int findNumberOfItems() {
		log.debug("Counting number of courses ... ");
		return this.jdbcTemplate.queryForObject(FINF_NUMBER_OF_COURSES, Integer.class);
	}
}
