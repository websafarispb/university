package ru.stepev.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ru.stepev.model.ClassRoom;

public class ClassroomDao {
	
	private static final String CREATE_CLASSROOM_QUERY = "INSERT INTO classrooms (classroom_address, classroom_capacity) values (?, ?)";
    private static final String GET_ALL = "SELECT * FROM classrooms";
   
    private JdbcTemplate jdbcTemplate;

    public ClassroomDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(ClassRoom classroom) {
    	jdbcTemplate.update(CREATE_CLASSROOM_QUERY, classroom.getAddress(), classroom.getCapacity());
    }
    
    private final RowMapper<ClassRoom> classroomRowMapper = (resultSet, rowNum) -> {
    	ClassRoom classroom = new ClassRoom(resultSet.getInt("classroom_id"), resultSet.getString("classroom_address"), resultSet.getInt("classroom_capacity"));
        return classroom;
    };

    public List<ClassRoom> findAllCourses() {
        return this.jdbcTemplate.query( GET_ALL, classroomRowMapper);
    }

    public void createClassrooms(List<ClassRoom> classrooms) {
    	for (ClassRoom classroom : classrooms) {
			create(classroom);
		}
    }
}
