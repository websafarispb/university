package ru.stepev.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.jdbc.JdbcTestUtils;

import ru.stepev.config.TestConfig;
import ru.stepev.model.Course;
import ru.stepev.model.Gender;
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
	public void givenCreate_whenGroupCreate_thenGroupWasCreated() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add((new Course(3, "Chemistry", "Chem")));
		courses.add((new Course(4, "Physics", "Phy")));
		List<Student> students = new ArrayList<>();
		students.add(new Student(1, 123, "Peter", "Petrov", LocalDate.of(2020, 9, 3), "webPP@mail.ru", Gender.MALE,
				"City17", courses));
		students.add(new Student(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses));
		int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "GROUPS") + 1;
		Group newGroup = new Group(5, "e2e2", students);
		String inquryForOneGroup = String.format("id = %d AND group_name = '%s' ", newGroup.getId(),
				newGroup.getName());
		String inquryForStudentsGroups = String.format(
				"student_id = %d AND group_id = %d OR student_id = %d AND group_id = %d", 1, newGroup.getId(), 2,
				newGroup.getId());
		int expectedCountRowsWichHaveOneGroup = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "GROUPS",
				inquryForOneGroup) + 1;
		int expectedRowInStudentsGroups = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "STUDENTS_GROUPS",
				inquryForStudentsGroups) + 2;

		groupDao.create(newGroup);

		int actualCountRowsWichHaveOneGroup = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "GROUPS",
				inquryForOneGroup);
		int actualRowInStudentsGroups = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "STUDENTS_GROUPS",
				inquryForStudentsGroups);
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "GROUPS");
		assertEquals(expectedRows, actualRows);
		assertEquals(expectedCountRowsWichHaveOneGroup, actualCountRowsWichHaveOneGroup);
		assertEquals(expectedRowInStudentsGroups, actualRowInStudentsGroups);
	}

	@Test
	public void givenUpdateGroupByIdNotExist_whenGroupUpdate_thenGetObjectNotFoundException() throws Exception {
		try {
			groupDao.update(new Group(200, "a2a2"));
			fail("Expected Exception");
		} catch (Exception e) {
			assertEquals("Group with Id = 200 not found !!!", e.getMessage());
		}
	}

	@Test
	public void givenUpdateGroupById_whenGroupUpdate_thenGroupWasUpdated() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		List<Student> students = new ArrayList<>();
		students.add(new Student(3, 125, "Peter", "Ivanov", LocalDate.of(2020, 9, 5), "webPI@mail.ru", Gender.FEMALE,
				"City19", courses));
		students.add(new Student(5, 227, "Irina", "Stepanova", LocalDate.of(2020, 9, 7), "Ivanov@mail.ru",
				Gender.FEMALE, "City11", courses));
		int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "GROUPS");
		Group updatedGroup = new Group(1, "a2a2", students);
		String inquryForOneGroup = String.format("id = %d AND group_name = '%s' ", updatedGroup.getId(),
				updatedGroup.getName());
		String inquryForStudentsGroups = String.format(
				"student_id = %d AND group_id = %d OR student_id = %d AND group_id = %d", 3, updatedGroup.getId(), 5,
				updatedGroup.getId());
		int expectedCountRowsWichHaveOneGroup = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "GROUPS",
				inquryForOneGroup);
		int expectedRowInStudentsGroups = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "STUDENTS_GROUPS",
				inquryForStudentsGroups) + 2;

		groupDao.update(updatedGroup);

		int actualCountRowsWichHaveOneGroup = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "GROUPS",
				inquryForOneGroup);
		int actualRowInStudentsGroups = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "STUDENTS_GROUPS",
				inquryForStudentsGroups);
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "GROUPS");
		assertEquals(expectedRows, actualRows);
		assertEquals(expectedCountRowsWichHaveOneGroup, actualCountRowsWichHaveOneGroup);
		assertEquals(expectedRowInStudentsGroups, actualRowInStudentsGroups);
	}

	@Test
	public void givenDeleteGroupByIdNotExist_whenGroupDelete_thenGetObjectNotFoundException() throws Exception {
		try {
			groupDao.delete(200);
			fail("Expected Exception");
		} catch (Exception e) {
			assertEquals("Group with Id = 200 not found !!!", e.getMessage());
		}
	}

	@Test
	public void givenDeleteGroup_whenDeleteGroupById_thenGroupWasDeleted() throws Exception {
		int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "GROUPS") - 1;
		groupDao.delete(3);
		int actualRow = JdbcTestUtils.countRowsInTable(jdbcTemplate, "GROUPS");
		assertEquals(expectedRows, actualRow);
	}

	@Test
	public void givenFindGroupByIdNotExist_whenFindGroupById_thenGetObjectNotFoundException() throws Exception {
		try {
			groupDao.findById(200);
			fail("Expected Exception");
		} catch (Exception e) {
			assertEquals("Group with Id = 200 not found !!!", e.getMessage());
		}
	}

	@Test
	public void givenFindGroupById_whenFindGroupById_thenGroupWasFound() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add((new Course(3, "Chemistry", "Chem")));
		courses.add((new Course(4, "Physics", "Phy")));
		List<Student> students = new ArrayList<>();
		students.add(new Student(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses));
		Group expected = new Group(2, "b2b2", students);
		Group actual = groupDao.findById(2);
		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void givenFindAll_whenFindAllGroups_whenAllGroupWasFound() throws Exception {
		int expectedRow = 4;
		int actualRow = groupDao.findAll().size();
		assertThat(expectedRow).isEqualTo(actualRow);
	}

	@Test
	public void givenFindGroupByStudentId_whenFindGroupByStudentId_thenGroupWasFound() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add((new Course(3, "Chemistry", "Chem")));
		courses.add((new Course(4, "Physics", "Phy")));
		List<Student> students = new ArrayList<>();
		students.add(new Student(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses));
		List<Group> expected = new ArrayList<>();
		expected.add(new Group(2, "b2b2", students));
		students = new ArrayList<>();
		students.add(new Student(1, 123, "Peter", "Petrov", LocalDate.of(2020, 9, 3), "webPP@mail.ru", Gender.MALE,
				"City17", courses));
		students.add(new Student(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses));
		expected.add(new Group(5, "e2e2", students));
		
		List<Group> actual = groupDao.findByStudentId(2);
		
		assertThat(expected).isEqualTo(actual);
	}
}
