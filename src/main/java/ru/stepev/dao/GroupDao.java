package ru.stepev.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import ru.stepev.dao.rowmapper.GroupRowMapper;
import ru.stepev.model.Group;
import ru.stepev.model.Student;

@Component
public class GroupDao {

	private static final String GET_ALL_GROUPS = "SELECT * FROM GROUPS ";
	private static final String CREATE_GROUP_QUERY = "INSERT INTO groups ( group_name) VALUES ( ?)";
	private static final String ASSIGN_STUDENT = "INSERT INTO students_groups (student_id, group_id) VALUES (?, ?)";
	private static final String GET_BY_STUDENT_ID = "SELECT groups.group_id, groups.group_name "
			+ " FROM groups INNER JOIN students_groups  " + " ON students_groups.group_id = groups.group_id "
			+ " WHERE students_groups.student_id = ?";
	private static final String GET_GROUP_ID_BY_NAME = "SELECT group_id FROM groups WHERE group_name = ?";
	private static final String UPDATE_BY_GROUP_ID = "UPDATE groups SET group_name = ? WHERE group_id = ?";
	private static final String DELETE_GROUP_BY_ID = "DELETE FROM groups WHERE group_id = ?";
	private static final String FIND_GROUP_BY_ID = "SELECT * FROM groups WHERE group_id = ?";

	@Autowired
	private GroupRowMapper groupRowMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public GroupDao(JdbcTemplate jdbcTemplate, GroupRowMapper groupRowMapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.groupRowMapper = groupRowMapper;
	}

	public void create(Group group) {
		jdbcTemplate.update(CREATE_GROUP_QUERY, group.getName());
		group.setId(jdbcTemplate.queryForObject(GET_GROUP_ID_BY_NAME, Integer.class, group.getName()));
	}

	public void update(Group group, int groupId) {
		jdbcTemplate.update(UPDATE_BY_GROUP_ID, group.getName(), groupId);
	}

	public void delete(int groupId) {
		jdbcTemplate.update(DELETE_GROUP_BY_ID, groupId);
	}

	public Group findById(int groupId) {
		return this.jdbcTemplate.queryForObject(FIND_GROUP_BY_ID, groupRowMapper, groupId);
	}

	public List<Group> findAll() {
		return this.jdbcTemplate.query(GET_ALL_GROUPS, groupRowMapper);
	}

	public void assignToStudents(Group group, List<Student> students) {
		for (Student student : students) {
			jdbcTemplate.update(ASSIGN_STUDENT, student.getId(), group.getId());
		}
	}

	public List<Group> getGroupByStudentId(int student_id) {
		Object[] objects = new Object[] { student_id };
		return this.jdbcTemplate.query(GET_BY_STUDENT_ID, objects, groupRowMapper);
	}
}
