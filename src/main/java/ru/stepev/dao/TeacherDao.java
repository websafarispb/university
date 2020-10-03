package ru.stepev.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.function.Predicate;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import ru.stepev.dao.rowmapper.TeacherRowMapper;
import ru.stepev.exceptions.ObjectNotFoundException;
import ru.stepev.model.Teacher;

@Component
public class TeacherDao {

	private static final String GET_ALL = "SELECT * FROM teachers";
	private static final String FIND_TEACHER_BY_ID = "SELECT * FROM teachers WHERE id = ?";
	private static final String CREATE_TEACHER_QUERY = "INSERT INTO teachers (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
	private static final String DELETE_TEACHER_QUERY = "DELETE FROM teachers WHERE id = ?";
	private static final String UPDATE_BY_TEACHER_ID = "UPDATE teachers SET first_name = ?, last_name = ?, birthday = ?, email = ?, gender= ?, address = ? WHERE id = ?";
	private static final String ADD_COURSE = "INSERT INTO teachers_courses (teacher_id, course_id) VALUES (?, ?)";
	private static final String DELETE_COURSE = "DELETE FROM teachers_courses WHERE teacher_id = ? AND course_id = ?";

	private TeacherRowMapper teacherRowMapper;
	private JdbcTemplate jdbcTemplate;

	public static <T> Predicate<T> not(Predicate<T> t) {
		return t.negate();
	}

	public TeacherDao(JdbcTemplate jdbcTemplate, TeacherRowMapper teacherRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.teacherRowMapper = teacherRowMapper;
	}

	@Transactional
	public void create(Teacher teacher) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(CREATE_TEACHER_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, teacher.getPersonalNumber());
			statement.setString(2, teacher.getFirstName());
			statement.setString(3, teacher.getLastName());
			statement.setObject(4, teacher.getBirthday());
			statement.setString(5, teacher.getEmail());
			statement.setString(6, teacher.getGender().toString());
			statement.setString(7, teacher.getAddress());
			return statement;
		}, keyHolder);
		teacher.setId((int) keyHolder.getKeys().get("id"));
		teacher.getCourses().forEach(c -> jdbcTemplate.update(ADD_COURSE, teacher.getId(), c.getId()));
	}

	@Transactional
	public void update(Teacher teacher) {
		if (jdbcTemplate.update(UPDATE_BY_TEACHER_ID, teacher.getFirstName(), teacher.getLastName(),
				teacher.getBirthday(), teacher.getEmail(), teacher.getGender().toString(), teacher.getAddress(),
				teacher.getId()) == 0) {
			throw new ObjectNotFoundException(String.format("Teacher with Id = %d not found !!!", teacher.getId()));

		} else {
			Teacher updatedTeacher = findById(teacher.getId());
			updatedTeacher.getCourses().stream().filter(not(teacher.getCourses()::contains))
					.forEach(c -> jdbcTemplate.update(DELETE_COURSE, teacher.getId(), c.getId()));
			teacher.getCourses().stream().filter(not(updatedTeacher.getCourses()::contains))
					.forEach(c -> jdbcTemplate.update(ADD_COURSE, teacher.getId(), c.getId()));
		}
	}

	public void delete(int teacherId) {
		if (jdbcTemplate.update(DELETE_TEACHER_QUERY, teacherId) == 0) {
			throw new ObjectNotFoundException(String.format("Teacher with Id = %d not found !!!", teacherId));
		}
	}

	public Teacher findById(int teacherId) {
		try {
			return jdbcTemplate.queryForObject(FIND_TEACHER_BY_ID, teacherRowMapper, teacherId);
		} catch (EmptyResultDataAccessException e) {
			throw new ObjectNotFoundException(String.format("Teacher with Id = %d not found !!!", teacherId), e);
		}
	}

	public List<Teacher> findAll() {
		return jdbcTemplate.query(GET_ALL, teacherRowMapper);
	}
}
