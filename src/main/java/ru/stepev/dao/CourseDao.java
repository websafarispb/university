package ru.stepev.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ru.stepev.model.Course;

public class CourseDao {
	
	private static final String CREATE_COURSE_QUERY = "INSERT INTO courses (course_name, course_description) values (?, ?)";
    private static final String GET_ALL = "SELECT * FROM courses";
   
    private JdbcTemplate jdbcTemplate;

    public CourseDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(Course course) {
    	jdbcTemplate.update(CREATE_COURSE_QUERY, course.getName(), course.getDescription());
    }
    
    private final RowMapper<Course> courseRowMapper = (resultSet, rowNum) -> {
    	Course course = new Course(resultSet.getInt("course_id"), resultSet.getString("course_name"), resultSet.getString("course_description"));
        return course;
    };

    public List<Course> findAllCourses() {
        return this.jdbcTemplate.query( GET_ALL, courseRowMapper);
    }
    
    public void createCourses(List<Course> courses) {
    	for (Course course : courses) {
			create(course);
		}
    }
}
