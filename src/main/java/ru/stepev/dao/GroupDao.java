package ru.stepev.dao;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ru.stepev.model.Group;
import ru.stepev.model.Student;

public class GroupDao {

	private static final String GET_ALL_GROUPS = "SELECT * FROM GROUPS ";
	private static final String CREATE_GROUP_QUERY = "INSERT INTO groups ( group_name) VALUES ( ?)";
	private static final String ASSIGN_STUDENT = "INSERT INTO students_groups (student_id, group_id) VALUES (?, ?)";
	private static final String GET_GROUP_ID_BY_STUDENT_ID = "SELECT group_id FROM students_groups WHERE student_id = ?";
	
	private static final String GET_BY_STUDENT_ID = "SELECT groups.group_id, groups.group_name " 
												+ " FROM groups INNER JOIN students_groups  " 
												+ " ON students_groups.group_id = groups.group_id " 
												+ " WHERE students_groups.student_id = ?";

	private JdbcTemplate jdbcTemplate;

	public GroupDao(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	public void create(Group group) {
		jdbcTemplate.update(CREATE_GROUP_QUERY, group.getName());
	}

	private final RowMapper<Group> groupRowMapper = (resultSet, rowNum) -> {
		Group group = new Group(resultSet.getInt("group_id"), resultSet.getString("group_name"));
		return group;
	};

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
	
	public void createGroups(List<Group>groups) {
		for (Group group : groups) {
			create(group);
		}	
	}
}
