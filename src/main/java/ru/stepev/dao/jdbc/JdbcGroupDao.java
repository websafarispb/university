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
import ru.stepev.dao.GroupDao;
import ru.stepev.dao.jdbc.rowmapper.GroupRowMapper;
import ru.stepev.exception.EntityCouldNotBeenCreatedException;
import ru.stepev.exception.EntityCouldNotBeenDeletedException;
import ru.stepev.exception.EntityCouldNotBeenUpdatedException;
import ru.stepev.model.Group;

@Component
@Slf4j
public class JdbcGroupDao implements GroupDao {

	private static final String GET_ALL_GROUPS = "SELECT * FROM GROUPS ";
	private static final String CREATE_GROUP_QUERY = "INSERT INTO groups ( group_name) VALUES ( ?)";
	private static final String ASSIGN_STUDENT = "INSERT INTO students_groups (student_id, group_id) VALUES (?, ?)";
	private static final String DELETE_STUDENT_FROM_GROUP = "DELETE FROM students_groups WHERE student_id = ? AND group_id = ?";
	private static final String GET_BY_STUDENT_ID = "SELECT groups.id, groups.group_name "
			+ " FROM groups INNER JOIN students_groups  " + " ON students_groups.group_id = groups.id "
			+ " WHERE students_groups.student_id = ?";
	private static final String UPDATE_BY_GROUP_ID = "UPDATE groups SET group_name = ? WHERE id = ?";
	private static final String DELETE_GROUP_BY_ID = "DELETE FROM groups WHERE id = ?";
	private static final String FIND_GROUP_BY_ID = "SELECT * FROM groups WHERE id = ?";
	private static final String FIND_GROUP_BY_NAME = "SELECT * FROM groups WHERE group_name = ?";
	private static final String FIND_GROUP_BY_GROUP_ID_AND_COURSE_ID = "SELECT DISTINCT  groups.id, groups.group_name FROM groups,students_groups INNER JOIN  students_courses "
			+ "ON students_courses.course_id = ?  WHERE students_groups.group_id = ? AND groups.id = ?";

	private GroupRowMapper groupRowMapper;
	private JdbcTemplate jdbcTemplate;

	public JdbcGroupDao(JdbcTemplate jdbcTemplate, GroupRowMapper groupRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.groupRowMapper = groupRowMapper;
	}

	public void create(Group group) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		if (jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(CREATE_GROUP_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, group.getName());
			return statement;
		}, keyHolder) == 0) {
			throw new EntityCouldNotBeenCreatedException(
					String.format("Group with name %s could not been created", group.getName()));
		}
		group.setId((int) (keyHolder.getKeys().get("id")));
		group.getStudents().stream().forEach(s -> jdbcTemplate.update(ASSIGN_STUDENT, s.getId(), group.getId()));
	}

	public void update(Group group) {
		if (jdbcTemplate.update(UPDATE_BY_GROUP_ID, group.getName(), group.getId()) != 0) {
			Optional<Group> updatedGroup = findById(group.getId());
			updatedGroup.get().getStudents().stream().filter(s -> !group.getStudents().contains(s))
					.forEach(s -> jdbcTemplate.update(DELETE_STUDENT_FROM_GROUP, s.getId(), group.getId()));

			group.getStudents().stream().filter(s -> !updatedGroup.get().getStudents().contains(s))
					.forEach(s -> jdbcTemplate.update(ASSIGN_STUDENT, s.getId(), group.getId()));
		} else {
			throw new EntityCouldNotBeenUpdatedException(
					String.format("Group with name %s could not been updated", group.getName()));
		}
	}

	public void delete(int groupId) {
		if (jdbcTemplate.update(DELETE_GROUP_BY_ID, groupId) == 0) {
			throw new EntityCouldNotBeenDeletedException(
					String.format("Group with name %s could not been deleted", groupId));
		}
	}

	public Optional<Group> findById(int groupId) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(FIND_GROUP_BY_ID, groupRowMapper, groupId));
		} catch (EmptyResultDataAccessException e) {
			log.warn("Group with id {} was not found", groupId);
			return Optional.empty();
		}
	}

	public List<Group> findAll() {
		log.debug("Finding all groups...");
		return jdbcTemplate.query(GET_ALL_GROUPS, groupRowMapper);
	}

	public Optional<Group> findByStudentId(int studentId) {
		Object[] objects = new Object[] { studentId };
		try {
			return Optional.of(jdbcTemplate.queryForObject(GET_BY_STUDENT_ID, objects, groupRowMapper));
		} catch (EmptyResultDataAccessException e) {
			log.warn("Group with Student id {} was not found", studentId);
			return Optional.empty();
		}
	}

	public Optional<Group> findByGroupIdAndCourseId(int groupId, int courseId) {
		Object[] objects = new Object[] { courseId, groupId, groupId };
		try {
			return Optional
					.of(jdbcTemplate.queryForObject(FIND_GROUP_BY_GROUP_ID_AND_COURSE_ID, objects, groupRowMapper));
		} catch (EmptyResultDataAccessException e) {
			log.warn("Group with id {} was not found", groupId);
			return Optional.empty();
		}
	}

	@Override
	public Optional<Group> findByName(String name) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(FIND_GROUP_BY_NAME, groupRowMapper, name));
		} catch (EmptyResultDataAccessException e) {
			log.warn("Group with name {} was not found", name);
			return Optional.empty();
		}
	}
}
