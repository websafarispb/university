package ru.stepev.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import ru.stepev.config.TestConfig;
import ru.stepev.model.Course;
import ru.stepev.model.Group;
import ru.stepev.model.Student;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class GroupDaoTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private GroupDao groupDao;

	@Test
	public void createOneGroupTest() throws Exception {
		int expectedRows = 4;	
		groupDao.create(new Group("e2e2"));
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "GROUPS");
		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void updateGroupByIdTest() throws Exception {
		Group group = new Group(4, "f2f2");
		groupDao.update(group);
		int expectedRows = 1;
		int actualRows = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "GROUPS",
				String.format("id = '%d' AND group_name = '%s'",
						group.getId(), group.getName()));
		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void deleteGroupByIdTest() throws Exception {
		int expectedRows = 3;
		groupDao.delete(3);
		int actualRow = JdbcTestUtils.countRowsInTable(jdbcTemplate, "GROUPS");
		assertEquals(expectedRows, actualRow);
	}

	@Test
	public void findGroupByIdTest() throws Exception {
		Group expected = new Group(2, "b2b2");
		Group actual = groupDao.findById(2);
		assertEquals(expected, actual);
	}

	@Test
	public void findAllCoursesInBasaTest() throws Exception {
		int expectedRow = 4;
		int actualRow = groupDao.findAll().size();
		assertEquals(expectedRow, actualRow);
	}

	@Test
	public void getGroupByStudentIdTest() {
		List<Group> expected = new ArrayList<>();
		expected.add(new Group(2, "b2b2"));
		List<Group> actual = groupDao.getGroupByStudentId(2);
		assertEquals(expected, actual);
	}

	@Test
	public void assignStudentsToGroup() throws Exception {
		List<Course> coursesForStudent = new ArrayList<>();
		coursesForStudent.add((new Course(3, "Chemistry", "Chem")));
		coursesForStudent.add((new Course(4, "Physics", "Phy")));
		List<Student> students = new ArrayList<>();
		students.add(new Student(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", coursesForStudent));
		Group group = new Group(1, "a2a2");
		groupDao.assignToStudents(group, students);
		int expectedRows = 1;
		int actualRows = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "STUDENTS_GROUPS",
				String.format("student_id = '%s' AND group_id = %d",
						2, 1));
		assertEquals(expectedRows, actualRows);
	}
}
