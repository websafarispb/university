package ru.stepev.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.stepev.dao.rowmapper.StudentRowMapper;
import ru.stepev.model.Course;
import ru.stepev.model.Student;

@Component
public class StudentDao {
	private static final String GET_ALL = "SELECT * FROM students";
	private static final String CREATE_STUDENT_QUERY = "INSERT INTO students (personal_number, first_name, last_name, birthday, email, gender, address) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
	private static final String ASSIGN_TO_COURSE = "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?)";
	private static final String GET_STUDENT_ID = "SELECT * FROM students WHERE first_name = ? and last_name = ?";
	private static final String GET_STUDENT_ID_BY_PERSONAL_NUMBER = "SELECT student_id FROM students WHERE personal_number = ?";
	private static final String FIND_STUDENT_BY_ID = "SELECT * FROM students WHERE student_id = ?";
	private static final String DELETE_BY_STUDENT_ID = "DELETE FROM students WHERE student_id = ?";
	private static final String UPDATE_BY_STUDENT_ID = "UPDATE students SET first_name = ?, last_name = ?, birthday = ?, email = ?, gender= ?, address = ? WHERE student_id = ?";

	@Autowired
	private StudentRowMapper studentRowMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public StudentDao(JdbcTemplate jdbcTemplate, StudentRowMapper studentRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.studentRowMapper = studentRowMapper;
	}

	public void create(Student student) {
		jdbcTemplate.update(CREATE_STUDENT_QUERY, student.getPersonalNumber(), student.getFirstName(),
				student.getLastName(), student.getBirthday(), student.getEmail(), student.getGender().name(),
				student.getAddress());
		student.setId(jdbcTemplate.queryForObject(GET_STUDENT_ID_BY_PERSONAL_NUMBER, Integer.class,
				student.getPersonalNumber()));
	}

	public void update(Student student, int studentId) {
		jdbcTemplate.update(UPDATE_BY_STUDENT_ID, student.getFirstName(), student.getLastName(),
				student.getBirthday().toString(), student.getEmail(), student.getGender().toString(),
				student.getAddress(), studentId);
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

	public void assignToCourse(Student student) {
		for (Course course : student.getCourses()) {
			jdbcTemplate.update(ASSIGN_TO_COURSE, student.getId(), course.getId());
		}
	}

	public List<Student> findStudentByFirstAndLastNames(String firstName, String lastName) {
		Object[] objects = new Object[] { firstName, lastName };
		return this.jdbcTemplate.query(GET_STUDENT_ID, objects, studentRowMapper);
	}
}
