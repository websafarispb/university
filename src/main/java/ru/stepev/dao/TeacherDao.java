package ru.stepev.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ru.stepev.model.Course;
import ru.stepev.model.Teacher;

public class TeacherDao {
	
	private static final String GET_ALL = "SELECT * FROM teachers";
	private static final String CREATE_TEACHER_QUERY = "INSERT INTO teachers (first_name, last_name, birthday, email, gender, address) VALUES ( ?, ?, ?, ?, ?, ?)";
	private static final String DELETE = "DELETE FROM teachers WHERE teacher_id = ?";
	private static final String ASSIGN_TO_COURSE = "INSERT INTO teachers_courses (teacher_id, course_id) VALUES (?, ?)";
	private static final String DELETE_FROM_COURSE = "DELETE FROM teachers_courses WHERE teacher_id = ? AND course_id = ?";

	private JdbcTemplate jdbcTemplate;

	public TeacherDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void create(Teacher teacher) {
		jdbcTemplate.update(CREATE_TEACHER_QUERY, teacher.getFirstName(), teacher.getLastName(), teacher.getBirthday(),
				teacher.getEmail(), teacher.getGender().name(), teacher.getAddress());
	}
	
	private final RowMapper<Teacher> teacherRowMapper = (resultSet, rowNum) -> {
    	Teacher teacher = new Teacher(resultSet.getInt("teacher_id"), resultSet.getString("first_name"), resultSet.getString("last_name"), resultSet.getString("birthday"), resultSet.getString("email"), resultSet.getString("gender"), resultSet.getString("address"));
        return teacher;
    };

    public List<Teacher> findAllTeacher() {
        return this.jdbcTemplate.query( GET_ALL, teacherRowMapper);
    }

	public void assignToCourse(Teacher teacher) {
		for (Course course : teacher.getCourses()) {
			jdbcTemplate.update(ASSIGN_TO_COURSE, teacher.getId(), course.getId());
		}
	}
	
	public void createTeachers(List <Teacher> teachers) {
		for (Teacher teacher : teachers) {
			create(teacher);
		}
	}
}
