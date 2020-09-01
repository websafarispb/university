package ru.stepev.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ru.stepev.model.Course;
import ru.stepev.model.Student;

public class StudentDao {
	private static final String GET_ALL = "SELECT * FROM students";
	private static final String CREATE_STUDENT_QUERY = "INSERT INTO students (first_name, last_name, birthday, email, gender, address) VALUES ( ?, ?, ?, ?, ?, ?)";
	private static final String DELETE = "DELETE FROM students WHERE student_id = ?";
	private static final String GET_BY_COURSE_NAME = "SELECT students.student_id, students.group_id, students.first_name, students.last_name "
			+ "FROM students_courses " + "INNER  JOIN students "
			+ "ON students_courses.student_id = students.student_id " + "INNER  JOIN courses "
			+ "ON students_courses.course_id = courses.course_id " + "WHERE courses.course_name = ?";
	private static final String ASSIGN_TO_COURSE = "INSERT INTO students_courses (student_id, course_id) VALUES (?, ?)";
	private static final String DELETE_FROM_COURSE = "DELETE FROM students_courses WHERE student_id = ? AND course_id = ?";
	private static final String GET_STUDENT_ID = "SELECT * FROM students WHERE first_name = ? and last_name = ?";

	private JdbcTemplate jdbcTemplate;

	public StudentDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void create(Student student) {
		jdbcTemplate.update(CREATE_STUDENT_QUERY, student.getFirstName(), student.getLastName(), student.getBirthday(),
				student.getEmail(), student.getGender().name(), student.getAddress());
	}

	private final RowMapper<Student> studentRowMapper = (resultSet, rowNum) -> {
		Student student = new Student(resultSet.getInt("student_id"), resultSet.getString("first_name"),
				resultSet.getString("last_name"), resultSet.getString("birthday"), resultSet.getString("email"),
				resultSet.getString("gender"), resultSet.getString("address"));
		return student;
	};

	public List<Student> findAllStudents() {
		return this.jdbcTemplate.query(GET_ALL, studentRowMapper);
	}

	public void assignToCourse(Student student, List<Course> courses) {
		for (Course course : courses) {
			jdbcTemplate.update(ASSIGN_TO_COURSE, student.getId(), course.getId());
		}
	}

	public List<Student> findStudentIdByFirstAndLastNames(String firstName, String lastName) {
		Object[] objects = new Object[] { firstName, lastName };
		return this.jdbcTemplate.query(GET_STUDENT_ID, objects, studentRowMapper);
	}

	public void createStudents(List<Student> students) {
		for (Student student : students) {
			create(student);
		}
	}
}
