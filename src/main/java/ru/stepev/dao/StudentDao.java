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
import ru.stepev.dao.rowmapper.StudentRowMapper;
import ru.stepev.model.Course;
import ru.stepev.model.Student;

@Component
public class StudentDao {
	private static final String GET_ALL = "SELECT * FROM students";
	private static final String CREATE_STUDENT_QUERY = "INSERT INTO students (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
	private static final String ASSIGN_TO_COURSE = "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?)";
	private static final String RESIGN_FROM_COURSE = "DELETE FROM students_courses WHERE student_id = ? AND course_id = ?";
	private static final String GET_STUDENT_ID = "SELECT * FROM students WHERE first_name = ? and last_name = ?";
	private static final String FIND_STUDENT_BY_ID = "SELECT * FROM students WHERE id = ?";
	private static final String DELETE_BY_STUDENT_ID = "DELETE FROM students WHERE id = ?";
	private static final String UPDATE_BY_STUDENT_ID = "UPDATE students SET first_name = ?, last_name = ?, birthday = ?, email = ?, gender= ?, address = ? WHERE id = ?";
	private static final String FIND_ALL_COURSE_BY_STUDENT_ID = "SELECT * FROM "
			+ "courses INNER JOIN students_courses ON  courses.id = students_courses.course_id WHERE students_courses.student_id = ?";

	@Autowired
	private CourseRowMapper courseRowMapper;
	private StudentRowMapper studentRowMapper;
	private JdbcTemplate jdbcTemplate;

	public StudentDao(JdbcTemplate jdbcTemplate, StudentRowMapper studentRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.studentRowMapper = studentRowMapper;
	}

	public void create(Student student) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(CREATE_STUDENT_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			statement.setInt(1, student.getPersonalNumber());
			statement.setString(2, student.getFirstName());
			statement.setString(3, student.getLastName());
			statement.setString(4, student.getBirthday().toString());
			statement.setString(5, student.getEmail());
			statement.setString(6, student.getGender().toString());
			statement.setString(7, student.getAddress());
			return statement;
		}, keyHolder);
		student.setId((int) keyHolder.getKeys().get("id"));
		student.getCourses().forEach(c -> jdbcTemplate.update(ASSIGN_TO_COURSE, student.getId(), c.getId()));

	}

	public void update(Student student) {
		jdbcTemplate.update(UPDATE_BY_STUDENT_ID, student.getFirstName(), student.getLastName(),
				student.getBirthday().toString(), student.getEmail(), student.getGender().toString(),
				student.getAddress(), student.getId());
		findAllCourseOfStudent(student.getId()).forEach(c -> jdbcTemplate.update(RESIGN_FROM_COURSE, student.getId(), c.getId()));
		student.getCourses().forEach(c -> jdbcTemplate.update(ASSIGN_TO_COURSE, student.getId(), c.getId()));
	}

	public void delete(int teacherId) {
		jdbcTemplate.update(DELETE_BY_STUDENT_ID, teacherId);
	}

	public Student findById(int teacherId) {
		return this.jdbcTemplate.queryForObject(FIND_STUDENT_BY_ID, studentRowMapper, teacherId);
	}

	public List<Student> findAllStudents() {
		return this.jdbcTemplate.query(GET_ALL, studentRowMapper);
	}

	public List<Student> findStudentByFirstAndLastNames(String firstName, String lastName) {
		Object[] objects = new Object[] { firstName, lastName };
		return this.jdbcTemplate.query(GET_STUDENT_ID, objects, studentRowMapper);
	}

	public List<Course> findAllCourseOfStudent(int studentId) {
		return jdbcTemplate.query(FIND_ALL_COURSE_BY_STUDENT_ID, courseRowMapper, studentId);
	}
}
