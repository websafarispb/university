package ru.stepev.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ru.stepev.dao.rowmapper.CourseRowMapper;
import ru.stepev.dao.rowmapper.TeacherRowMapper;
import ru.stepev.model.Course;
import ru.stepev.model.Teacher;

@Component
public class TeacherDao {

	private static final String GET_ALL = "SELECT * FROM teachers";
	private static final String FIND_TEACHER_BY_ID = "SELECT * FROM teachers WHERE id = ?";
	private static final String CREATE_TEACHER_QUERY = "INSERT INTO teachers (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
	private static final String DELETE_BY_TEACHER_ID = "DELETE FROM teachers WHERE id = ?";
	private static final String UPDATE_BY_TEACHER_ID = "UPDATE teachers SET first_name = ?, last_name = ?, birthday = ?, email = ?, gender= ?, address = ? WHERE id = ?";
	private static final String ASSIGN_TO_COURSE = "INSERT INTO teachers_courses (teacher_id, course_id) VALUES (?, ?)";
	private static final String RESIGN_FROM_COURSE = "DELETE FROM teachers_courses WHERE teacher_id = ? AND course_id = ?";
	private static final String FIND_ALL_COURSE_BY_TEACHER_ID = "SELECT * FROM "
			+ "courses INNER JOIN teachers_courses ON  courses.id = teachers_courses.course_id WHERE teachers_courses.teacher_id = ?";

	@Autowired
	private CourseRowMapper courseRowMapper;
	private TeacherRowMapper teacherRowMapper;
	private JdbcTemplate jdbcTemplate;

	public TeacherDao(JdbcTemplate jdbcTemplate, TeacherRowMapper teacherRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.teacherRowMapper = teacherRowMapper;
	}

	public void create(Teacher teacher) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(CREATE_TEACHER_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, teacher.getPersonalNumber());
			statement.setString(2, teacher.getFirstName());
			statement.setString(3, teacher.getLastName());
			statement.setString(4, teacher.getBirthday().toString());
			statement.setString(5, teacher.getEmail());
			statement.setString(6, teacher.getGender().toString());
			statement.setString(7, teacher.getAddress());
			return statement;
		}, keyHolder);
		teacher.setId((int) keyHolder.getKeys().get("id"));
		teacher.getCourses().forEach(c -> jdbcTemplate.update(ASSIGN_TO_COURSE, teacher.getId(), c.getId()));
	}

	public void update(Teacher teacher) {
		jdbcTemplate.update(UPDATE_BY_TEACHER_ID, teacher.getFirstName(), teacher.getLastName(),
				teacher.getBirthday().toString(), teacher.getEmail(), teacher.getGender().toString(),
				teacher.getAddress(), teacher.getId());
		findAllCourseOfTeacher(teacher.getId()).forEach(c->jdbcTemplate.update(RESIGN_FROM_COURSE, teacher.getId(), c.getId()));
		teacher.getCourses().forEach(c->jdbcTemplate.update(ASSIGN_TO_COURSE, teacher.getId(), c.getId()));
	}

	public void delete(int teacherId) {
		jdbcTemplate.update(DELETE_BY_TEACHER_ID, teacherId);
	}

	public Teacher findById(int teacherId) {
		Teacher teacher = this.jdbcTemplate.queryForObject(FIND_TEACHER_BY_ID, teacherRowMapper, teacherId);
		teacher.setCourses(findAllCourseOfTeacher(teacher.getId()));
		return teacher;
	}

	public List<Teacher> findAllTeacher() {
		   List<Teacher> teachers = jdbcTemplate.query(GET_ALL, teacherRowMapper);
	        teachers.forEach(t -> t.setCourses(findAllCourseOfTeacher(t.getId())));
	        return teachers;
	}

	public List<Course> findAllCourseOfTeacher(int teacherId) {
		return jdbcTemplate.query(FIND_ALL_COURSE_BY_TEACHER_ID, courseRowMapper, teacherId);
	}
}
