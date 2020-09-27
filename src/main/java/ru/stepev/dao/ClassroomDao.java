package ru.stepev.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ru.stepev.dao.rowmapper.ClassroomRowMapper;
import ru.stepev.model.Classroom;

@Component
public class ClassroomDao {

	private static final String CREATE_CLASSROOM_QUERY = "INSERT INTO classrooms (classroom_address, classroom_capacity) values (?, ?)";
	private static final String GET_ALL = "SELECT * FROM classrooms";
	private static final String FIND_CLASSROOM_BY_ID = "SELECT * FROM classrooms WHERE id = ?";
	private static final String UPDATE_CLASSROOM_BY_ID = "UPDATE classrooms SET classroom_address = ?, classroom_capacity = ? WHERE id = ?";
	private static final String DELETE_CLASSROOM_BY_ID = "DELETE FROM classrooms WHERE id = ?";

	private ClassroomRowMapper classroomRowMapper;
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public ClassroomDao(JdbcTemplate jdbcTemplate, ClassroomRowMapper classroomRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.classroomRowMapper = classroomRowMapper;
	}

	public void create(Classroom classroom) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(CREATE_CLASSROOM_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, classroom.getAddress());
			statement.setInt(2, classroom.getCapacity());
			return statement;
		}, keyHolder);
		classroom.setId((int) keyHolder.getKeys().get("id"));
	}

	public void update(Classroom classroom) {
		jdbcTemplate.update(UPDATE_CLASSROOM_BY_ID, classroom.getAddress(), classroom.getCapacity(), classroom.getId() );
	}

	public void delete(int classroomId) {
		jdbcTemplate.update(DELETE_CLASSROOM_BY_ID, classroomId);
	}

	public Classroom findById(int classroomId) {
		return this.jdbcTemplate.queryForObject(FIND_CLASSROOM_BY_ID, classroomRowMapper, classroomId);
	}

	public List<Classroom> findAll() {
		return this.jdbcTemplate.query(GET_ALL, classroomRowMapper);
	}
}
