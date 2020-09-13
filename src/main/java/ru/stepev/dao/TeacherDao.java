package ru.stepev.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.stepev.dao.rowmapper.TeacherRowMapper;
import ru.stepev.model.Course;
import ru.stepev.model.Teacher;

@Component
public class TeacherDao {

	private static final String GET_ALL = "SELECT * FROM teachers";
	private static final String FIND_TEACHER_BY_ID = "SELECT * FROM teachers WHERE teacher_id = ?";
	private static final String CREATE_TEACHER_QUERY = "INSERT INTO teachers (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
	private static final String DELETE_BY_TEACHER_ID = "DELETE FROM teachers WHERE teacher_id = ?";
	private static final String UPDATE_BY_TEACHER_ID = "UPDATE teachers SET first_name = ?, last_name = ?, birthday = ?, email = ?, gender= ?, address = ? WHERE teacher_id = ?";
	private static final String ASSIGN_TO_COURSE = "INSERT INTO teachers_courses (teacher_id, course_id) VALUES (?, ?)";
	private static final String GET_TEACHER_ID_BY_PERSONAL_NUMBER = "SELECT teacher_id FROM teachers WHERE personal_number = ?";

	@Autowired
	private TeacherRowMapper teacherRowMapper;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public TeacherDao(JdbcTemplate jdbcTemplate, TeacherRowMapper teacherRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.teacherRowMapper = teacherRowMapper;
	}

	public void create(Teacher teacher) {
		jdbcTemplate.update(CREATE_TEACHER_QUERY, teacher.getPersonalNumber(), teacher.getFirstName(),
				teacher.getLastName(), teacher.getBirthday(), teacher.getEmail(), teacher.getGender().name(),
				teacher.getAddress());
		teacher.setId(jdbcTemplate.queryForObject(GET_TEACHER_ID_BY_PERSONAL_NUMBER, Integer.class,
				teacher.getPersonalNumber()));
	}

	public void update(Teacher teacher, int teacherId) {
		jdbcTemplate.update(UPDATE_BY_TEACHER_ID, teacher.getFirstName(), teacher.getLastName(),
				teacher.getBirthday().toString(), teacher.getEmail(), teacher.getGender().toString(),
				teacher.getAddress(), teacherId);
	}

	public void delete(int teacherId) {
		jdbcTemplate.update(DELETE_BY_TEACHER_ID, teacherId);
	}

	public Teacher findById(int teacherId) {
		return this.jdbcTemplate.queryForObject(FIND_TEACHER_BY_ID, teacherRowMapper, teacherId);
	}

	public List<Teacher> findAllTeacher() {
		return this.jdbcTemplate.query(GET_ALL, teacherRowMapper);
	}

	public void assignToCourse(Teacher teacher) {
		for (Course course : teacher.getCourses()) {
			jdbcTemplate.update(ASSIGN_TO_COURSE, teacher.getId(), course.getId());
		}
	}
}
