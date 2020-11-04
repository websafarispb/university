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
import ru.stepev.dao.TeacherDao;
import ru.stepev.dao.jdbc.rowmapper.TeacherRowMapper;
import ru.stepev.exception.EntityCouldNotBeenCreatedException;
import ru.stepev.exception.EntityCouldNotBeenUpdatedException;
import ru.stepev.model.Teacher;

@Component
@Slf4j
public class JdbcTeacherDao implements TeacherDao {

	private static final String GET_ALL = "SELECT * FROM teachers";
	private static final String FIND_TEACHER_BY_ID = "SELECT * FROM teachers WHERE id = ?";
	private static final String CREATE_TEACHER_QUERY = "INSERT INTO teachers (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
	private static final String DELETE_TEACHER_QUERY = "DELETE FROM teachers WHERE id = ?";
	private static final String UPDATE_BY_TEACHER_ID = "UPDATE teachers SET first_name = ?, last_name = ?, birthday = ?, email = ?, gender= ?, address = ? WHERE id = ?";
	private static final String ADD_COURSE = "INSERT INTO teachers_courses (teacher_id, course_id) VALUES (?, ?)";
	private static final String DELETE_COURSE = "DELETE FROM teachers_courses WHERE teacher_id = ? AND course_id = ?";

	private TeacherRowMapper teacherRowMapper;
	private JdbcTemplate jdbcTemplate;

	public JdbcTeacherDao(JdbcTemplate jdbcTemplate, TeacherRowMapper teacherRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.teacherRowMapper = teacherRowMapper;
	}

	@Transactional
	public void create(Teacher teacher) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		if (jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(CREATE_TEACHER_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, teacher.getPersonalNumber());
			statement.setString(2, teacher.getFirstName());
			statement.setString(3, teacher.getLastName());
			statement.setObject(4, teacher.getBirthday());
			statement.setString(5, teacher.getEmail());
			statement.setString(6, teacher.getGender());
			statement.setString(7, teacher.getAddress());
			return statement;
		}, keyHolder) == 0) {
			log.warn("Teacher with name {} could not been created",
					teacher.getFirstName() + " " + teacher.getLastName());
			throw new EntityCouldNotBeenCreatedException("Teacher could not been created!!!");
		}
		teacher.setId((int) keyHolder.getKeys().get("id"));
		teacher.getCourses().forEach(c -> jdbcTemplate.update(ADD_COURSE, teacher.getId(), c.getId()));
		log.debug("Teacher  was created");
	}

	@Transactional
	public void update(Teacher teacher) {
		if (jdbcTemplate.update(UPDATE_BY_TEACHER_ID, teacher.getFirstName(), teacher.getLastName(),
				teacher.getBirthday(), teacher.getEmail(), teacher.getGender().toString(), teacher.getAddress(),
				teacher.getId()) != 0) {
			Optional<Teacher> updatedTeacher = findById(teacher.getId());
			updatedTeacher.get().getCourses().stream().filter(not(teacher.getCourses()::contains))
					.forEach(c -> jdbcTemplate.update(DELETE_COURSE, teacher.getId(), c.getId()));
			teacher.getCourses().stream().filter(not(updatedTeacher.get().getCourses()::contains))
					.forEach(c -> jdbcTemplate.update(ADD_COURSE, teacher.getId(), c.getId()));
			log.debug("Teacher with name {} was updated", teacher.getFirstName() + " " + teacher.getLastName());
		} else {
			log.warn("Teacher with name {} could not been updated",
					teacher.getFirstName() + " " + teacher.getLastName());
			throw new EntityCouldNotBeenUpdatedException("Teacher could not been updated!!!");
		}
	}

	public void delete(int teacherId) {
		if (jdbcTemplate.update(DELETE_TEACHER_QUERY, teacherId) == 0) {
			log.warn("Teacher with id {} could not been deleted", teacherId);
			throw new EntityCouldNotBeenUpdatedException("Teacher could not been updated!!!");
		}
		log.debug("Teacher with id {} was deleted", teacherId);
	}

	public Optional<Teacher> findById(int teacherId) {
		try {
			Optional<Teacher> teacher = Optional.of(jdbcTemplate.queryForObject(FIND_TEACHER_BY_ID, teacherRowMapper, teacherId));
			log.debug("Teacher was found by ID {}", teacherId);
			return teacher;
		} catch (EmptyResultDataAccessException e) {
			log.warn("Teacher was not found by ID {}", teacherId);
			return Optional.empty();
		}
	}

	public List<Teacher> findAll() {
		log.debug("Finding all teachers ... ");
		return jdbcTemplate.query(GET_ALL, teacherRowMapper);
	}
}
