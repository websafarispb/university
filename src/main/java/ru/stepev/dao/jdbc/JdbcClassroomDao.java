package ru.stepev.dao.jdbc;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.stepev.dao.ClassroomDao;
import ru.stepev.dao.jdbc.rowmapper.ClassroomRowMapper;
import ru.stepev.exception.EntityCouldNotBeenCreatedException;
import ru.stepev.exception.EntityCouldNotBeenDeletedException;
import ru.stepev.exception.EntityCouldNotBeenUpdatedException;
import ru.stepev.model.Classroom;

@Component
@Slf4j
public class JdbcClassroomDao implements ClassroomDao {

	private static final String CREATE_CLASSROOM_QUERY = "INSERT INTO classrooms (classroom_address, classroom_capacity) values (?, ?)";
	private static final String GET_ALL = "SELECT * FROM classrooms";
	private static final String FIND_CLASSROOM_BY_ID = "SELECT * FROM classrooms WHERE id = ?";
	private static final String FIND_CLASSROOM_BY_ADDRESS = "SELECT * FROM classrooms WHERE classroom_address = ?";
	private static final String FIND_AND_SORT_BY_CAPACITY = "SELECT * FROM classrooms ORDER BY classroom_capacity ASC LIMIT ? OFFSET ?";
	private static final String FIND_AND_SORT_BY_ID = "SELECT * FROM classrooms ORDER BY id ASC LIMIT ? OFFSET ?";
	private static final String FIND_AND_SORT_BY_ADDRESS = "SELECT * FROM classrooms ORDER BY classroom_address ASC LIMIT ? OFFSET ?";
	private static final String UPDATE_CLASSROOM_BY_ID = "UPDATE classrooms SET classroom_address = ?, classroom_capacity = ? WHERE id = ?";
	private static final String DELETE_CLASSROOM_BY_ID = "DELETE FROM classrooms WHERE id = ?";
	private static final String FIND_NUMBER_OF_CLASSROOM = "SELECT COUNT(*) FROM classrooms";

	private ClassroomRowMapper classroomRowMapper;
	private JdbcTemplate jdbcTemplate;

	public JdbcClassroomDao(JdbcTemplate jdbcTemplate, ClassroomRowMapper classroomRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.classroomRowMapper = classroomRowMapper;
	}

	public void create(Classroom classroom) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		if (jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(CREATE_CLASSROOM_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, classroom.getAddress());
			statement.setInt(2, classroom.getCapacity());
			return statement;
		}, keyHolder) == 0) {
			throw new EntityCouldNotBeenCreatedException(
					String.format("Classroom  with address %s could not been created!!!", classroom.getAddress()));
		}
		classroom.setId((int) keyHolder.getKeys().get("id"));
	}

	public void update(Classroom classroom) {
		if (jdbcTemplate.update(UPDATE_CLASSROOM_BY_ID, classroom.getAddress(), classroom.getCapacity(),
				classroom.getId()) == 0) {
			throw new EntityCouldNotBeenUpdatedException(
					String.format("Classroom  with address %s could not been updated!!!", classroom.getAddress()));
		}
	}

	public void delete(int classroomId) {
		if (jdbcTemplate.update(DELETE_CLASSROOM_BY_ID, classroomId) == 0) {
			throw new EntityCouldNotBeenDeletedException(
					String.format("Classroom  with Id %s could not been deleted!!!", classroomId));
		}
	}

	public Optional<Classroom> findById(int classroomId) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(FIND_CLASSROOM_BY_ID, classroomRowMapper, classroomId));
		} catch (EmptyResultDataAccessException e) {
			log.warn("Classroom with id {} was not found", classroomId);
			return Optional.empty();
		}
	}

	public List<Classroom> findAll() {
		log.debug("Finding all classrooms...");
		return jdbcTemplate.query(GET_ALL, classroomRowMapper);
	}

	@Override
	public Optional<Classroom> findByAddress(String address) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(FIND_CLASSROOM_BY_ADDRESS, classroomRowMapper, address));
		} catch (EmptyResultDataAccessException e) {
			log.warn("Classroom with address {} was not found", address);
			return Optional.empty();
		}
	}

	@Override
	public List<Classroom> findAndSortByCapacity(int numberOfItems, int offset) {
		log.debug("Finding and sorting classroom by capacity ... ");
		Object[] objects = new Object[] { numberOfItems, offset };
		return jdbcTemplate.query(FIND_AND_SORT_BY_CAPACITY, objects, classroomRowMapper);
	}
	
	@Override
	public List<Classroom> findAndSortById(int numberOfItems, int offset) {
		log.debug("Finding and sorting classroom by id ... ");
		Object[] objects = new Object[] { numberOfItems, offset };
		return jdbcTemplate.query(FIND_AND_SORT_BY_ID, objects, classroomRowMapper);
	}
	
	@Override
	public List<Classroom> findAndSortByAddress(int numberOfItems, int offset) {
		log.debug("Finding and sorting classroom by address ... ");
		Object[] objects = new Object[] { numberOfItems, offset };
		return jdbcTemplate.query(FIND_AND_SORT_BY_ADDRESS, objects, classroomRowMapper);
	}

	@Override
	public int findNumberOfItems() {
		log.debug("Counting number of classroom ... ");
		return this.jdbcTemplate.queryForObject(FIND_NUMBER_OF_CLASSROOM, Integer.class);
	}
}
