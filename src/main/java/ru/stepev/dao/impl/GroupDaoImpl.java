package ru.stepev.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import ru.stepev.dao.GroupDao;
import ru.stepev.dao.rowmapper.GroupRowMapper;
import ru.stepev.model.Group;

@Component
public class GroupDaoImpl implements GroupDao{

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

	private GroupRowMapper groupRowMapper;
	private JdbcTemplate jdbcTemplate;

	public GroupDaoImpl(JdbcTemplate jdbcTemplate, GroupRowMapper groupRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.groupRowMapper = groupRowMapper;
	}

	public void create(Group group) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
			PreparedStatement statement = connection.prepareStatement(CREATE_GROUP_QUERY,
					Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, group.getName());
			return statement;
		}, keyHolder);
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
		}
	}

	public void delete(int groupId) {
		jdbcTemplate.update(DELETE_GROUP_BY_ID, groupId);
	}

	public Optional<Group> findById(int groupId) {
		try {
			return Optional.of(jdbcTemplate.queryForObject(FIND_GROUP_BY_ID, groupRowMapper, groupId));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}

	public List<Group> findAll() {
		return jdbcTemplate.query(GET_ALL_GROUPS, groupRowMapper);
	}

	public Optional<Group> findByStudentId(int studentId) {
		Object[] objects = new Object[] { studentId };
		try {
			return Optional.of(jdbcTemplate.queryForObject(GET_BY_STUDENT_ID, objects, groupRowMapper));
		} catch (EmptyResultDataAccessException e) {
			return Optional.empty();
		}
	}
}
