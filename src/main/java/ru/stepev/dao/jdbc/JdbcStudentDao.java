package ru.stepev.dao.jdbc;

import static java.util.function.Predicate.not;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import lombok.extern.slf4j.Slf4j;
import ru.stepev.dao.StudentDao;
import ru.stepev.dao.jdbc.rowmapper.StudentRowMapper;
import ru.stepev.exception.EntityCouldNotBeenCreatedException;
import ru.stepev.exception.EntityCouldNotBeenUpdatedException;
import ru.stepev.model.Student;

@Component
@Slf4j
public class JdbcStudentDao implements StudentDao {
	private static final String GET_ALL = "SELECT * FROM students";
	private static final String CREATE_STUDENT_QUERY = "INSERT INTO students (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
	private static final String ASSIGN_TO_COURSE = "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?)";
	private static final String DELETE_FROM_COURSE = "DELETE FROM students_courses WHERE student_id = ? AND course_id = ?";
	private static final String GET_STUDENT_ID = "SELECT * FROM students WHERE first_name = ? and last_name = ?";
	private static final String FIND_STUDENT_BY_ID = "SELECT * FROM students WHERE id = ?";
	private static final String DELETE_BY_STUDENT_ID = "DELETE FROM students WHERE id = ?";
	private static final String UPDATE_BY_STUDENT_ID = "UPDATE students SET first_name = ?, last_name = ?, birthday = ?, email = ?, gender= ?, address = ? WHERE id = ?";
	private static final String GET_STUDENT_BY_GROUP_ID = "SELECT * FROM students INNER JOIN students_groups ON  students.id = students_groups.student_id WHERE students_groups.group_id = ?";

	private StudentRowMapper studentRowMapper;
	private JdbcTemplate jdbcTemplate;

	public JdbcStudentDao(JdbcTemplate jdbcTemplate, StudentRowMapper studentRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.studentRowMapper = studentRowMapper;
	}

	@Transactional
	public void create(Student student) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		if (jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(CREATE_STUDENT_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, student.getPersonalNumber());
			statement.setString(2, student.getFirstName());
			statement.setString(3, student.getLastName());
			statement.setObject(4, student.getBirthday());
			statement.setString(5, student.getEmail());
			statement.setString(6, student.getGender());
			statement.setString(7, student.getAddress());
			return statement;
		}, keyHolder) == 0) {
			throw new EntityCouldNotBeenCreatedException(String.format("Student with name %s could not been created",
					student.getFirstName() + " " + student.getLastName()));
		}
		student.setId((int) keyHolder.getKeys().get("id"));
		student.getCourses().forEach(c -> jdbcTemplate.update(ASSIGN_TO_COURSE, student.getId(), c.getId()));
		log.debug("Student  was created");
	}

	@Transactional
	public void update(Student student) {
		if (jdbcTemplate.update(UPDATE_BY_STUDENT_ID, student.getFirstName(), student.getLastName(),
				student.getBirthday(), student.getEmail(), student.getGender().toString(), student.getAddress(),
				student.getId()) != 0) {
			Optional<Student> updatedSudent = findById(student.getId());
			updatedSudent.get().getCourses().stream().filter(not(student.getCourses()::contains))
					.forEach(c -> jdbcTemplate.update(DELETE_FROM_COURSE, student.getId(), c.getId()));
			student.getCourses().stream().filter(not(updatedSudent.get().getCourses()::contains))
					.forEach(c -> jdbcTemplate.update(ASSIGN_TO_COURSE, student.getId(), c.getId()));
		} else {
			throw new EntityCouldNotBeenUpdatedException(String.format("Student with name %s could not been updated",
					student.getFirstName() + " " + student.getLastName()));
		}
	}

	public void delete(int studentId) {
		if (jdbcTemplate.update(DELETE_BY_STUDENT_ID, studentId) == 0) {
			throw new EntityCouldNotBeenUpdatedException(
					String.format("Student with Id %s could not been deleted", studentId));
		}
	}

	public Optional<Student> findById(int studentId) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(FIND_STUDENT_BY_ID, studentRowMapper, studentId));
		} catch (EmptyResultDataAccessException e) {
			log.warn("Student with id {} was not found", studentId);
			return Optional.empty();
		}
	}

	public List<Student> findAll() {
		log.debug("Finding all students ... ");
		return jdbcTemplate.query(GET_ALL, studentRowMapper);
	}

	public List<Student> findByFirstAndLastNames(String firstName, String lastName) {
		log.debug("Finding student by first and last name ... ");
		Object[] objects = new Object[] { firstName, lastName };
		return jdbcTemplate.query(GET_STUDENT_ID, objects, studentRowMapper);
	}

	public List<Student> findByGroupId(int groupId) {
		log.debug("Finding student by group ID ... ");
		return jdbcTemplate.query(GET_STUDENT_BY_GROUP_ID, studentRowMapper, groupId);
	}
}
