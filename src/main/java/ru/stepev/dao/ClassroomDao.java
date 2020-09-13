package ru.stepev.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.stepev.dao.rowmapper.ClassroomRowMapper;
import ru.stepev.model.Classroom;

@Component
public class ClassroomDao {

	private static final String CREATE_CLASSROOM_QUERY = "INSERT INTO classrooms (classroom_address, classroom_capacity) values (?, ?)";
	private static final String GET_ALL = "SELECT * FROM classrooms";
	private static final String FIND_CLASSROOM_BY_ID = "SELECT * FROM classrooms WHERE classroom_id = ?";
	private static final String GET_CLASSROOM_ID_BY_ADDRESS = "SELECT classroom_id FROM classrooms WHERE classroom_address = ?";
	private static final String UPDATE_CLASSROOM_BY_ID = "UPDATE classrooms SET classroom_address = ?, classroom_capacity = ? WHERE classroom_id = ?";
	private static final String DELETE_CLASSROOM_BY_ID = "DELETE FROM classrooms WHERE classroom_id = ?";
	
	@Autowired
	private ClassroomRowMapper classroomRowMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public ClassroomDao(JdbcTemplate jdbcTemplate, ClassroomRowMapper classroomRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.classroomRowMapper = classroomRowMapper;
	}

	public void create(Classroom classroom) {
		jdbcTemplate.update(CREATE_CLASSROOM_QUERY, classroom.getAddress(), classroom.getCapacity());
		classroom.setId(jdbcTemplate.queryForObject(GET_CLASSROOM_ID_BY_ADDRESS, Integer.class, classroom.getAddress()));
	}
	
	public void update(Classroom classroom, int classroomId) {
		jdbcTemplate.update(UPDATE_CLASSROOM_BY_ID, classroom.getAddress(), classroom.getCapacity(), classroomId);
	}

	public void delete(int classroomId) {
		jdbcTemplate.update(DELETE_CLASSROOM_BY_ID, classroomId);
	}

	public Classroom findById(int classroomId) {
		return this.jdbcTemplate.queryForObject(FIND_CLASSROOM_BY_ID, classroomRowMapper, classroomId);
	}
	
	public List<Classroom> findAllClassrooms() {
		return this.jdbcTemplate.query(GET_ALL, classroomRowMapper);
	}
}
