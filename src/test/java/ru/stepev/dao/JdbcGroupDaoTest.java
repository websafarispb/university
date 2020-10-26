package ru.stepev.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.stepev.config.TestConfig;
import ru.stepev.model.Course;
import ru.stepev.model.Gender;
import ru.stepev.model.Group;
import ru.stepev.model.Student;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class JdbcGroupDaoTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private GroupDao groupDao;

	@Test
	public void givenCreate_whenGroupCreate_thenGroupWasCreated() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add((new Course(3, "Chemistry", "Chem")));
		courses.add((new Course(4, "Physics", "Phy")));
		List<Student> students = new ArrayList<>();
		students.add(new Student(6, 527, "Daria", "Ivanova", LocalDate.of(2020, 9, 7), "Ivanova@mail.ru", Gender.FEMALE,
				"City20", courses));
		students.add(new Student(7, 528, "Igor", "Petrov", LocalDate.of(2020, 9, 7), "Stepanov@mail.ru", Gender.MALE,
				"City20", courses));

		int expectedRows = countRowsInTable(jdbcTemplate, "GROUPS") + 1;
		Group newGroup = new Group(5, "e2e2", students);
		String inquryForOneGroup = String.format("id = %d AND group_name = '%s' ", newGroup.getId(),
				newGroup.getName());
		String inquryForStudentsGroups = String.format(
				"student_id = %d AND group_id = %d OR student_id = %d AND group_id = %d", 6, newGroup.getId(), 7,
				newGroup.getId());
		int expectedCountRowsWichHaveOneGroup = countRowsInTableWhere(jdbcTemplate, "GROUPS", inquryForOneGroup) + 1;
		int expectedRowInStudentsGroups = countRowsInTableWhere(jdbcTemplate, "STUDENTS_GROUPS",
				inquryForStudentsGroups) + 2;

		groupDao.create(newGroup);

		int actualCountRowsWichHaveOneGroup = countRowsInTableWhere(jdbcTemplate, "GROUPS", inquryForOneGroup);
		int actualRowInStudentsGroups = countRowsInTableWhere(jdbcTemplate, "STUDENTS_GROUPS", inquryForStudentsGroups);
		int actualRows = countRowsInTable(jdbcTemplate, "GROUPS");
		assertEquals(expectedRows, actualRows);
		assertEquals(expectedCountRowsWichHaveOneGroup, actualCountRowsWichHaveOneGroup);
		assertEquals(expectedRowInStudentsGroups, actualRowInStudentsGroups);
	}

	@Test
	public void givenUpdateGroupById_whenGroupUpdate_thenGroupWasUpdated() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		List<Student> students = new ArrayList<>();
		students.add(new Student(3, 125, "Peter", "Ivanov", LocalDate.of(2020, 9, 5), "webPI@mail.ru", Gender.FEMALE,
				"City19", courses));
		students.add(new Student(5, 227, "Irina", "Stepanova", LocalDate.of(2020, 9, 7), "Ivanov@mail.ru",
				Gender.FEMALE, "City11", courses));
		int expectedRows = countRowsInTable(jdbcTemplate, "GROUPS");
		Group updatedGroup = new Group(1, "a2a2", students);
		String inquryForOneGroup = String.format("id = %d AND group_name = '%s' ", updatedGroup.getId(),
				updatedGroup.getName());
		String inquryForStudentsGroups = String.format(
				"student_id = %d AND group_id = %d OR student_id = %d AND group_id = %d", 3, updatedGroup.getId(), 5,
				updatedGroup.getId());
		int expectedCountRowsWichHaveOneGroup = countRowsInTableWhere(jdbcTemplate, "GROUPS", inquryForOneGroup);
		int expectedRowInStudentsGroups = countRowsInTableWhere(jdbcTemplate, "STUDENTS_GROUPS",
				inquryForStudentsGroups) + 2;

		groupDao.update(updatedGroup);

		int actualCountRowsWichHaveOneGroup = countRowsInTableWhere(jdbcTemplate, "GROUPS", inquryForOneGroup);
		int actualRowInStudentsGroups = countRowsInTableWhere(jdbcTemplate, "STUDENTS_GROUPS", inquryForStudentsGroups);
		int actualRows = countRowsInTable(jdbcTemplate, "GROUPS");
		assertEquals(expectedRows, actualRows);
		assertEquals(expectedCountRowsWichHaveOneGroup, actualCountRowsWichHaveOneGroup);
		assertEquals(expectedRowInStudentsGroups, actualRowInStudentsGroups);
	}

	@Test
	public void givenDeleteGroup_whenDeleteGroupById_thenGroupWasDeleted() {
		int expectedRows = countRowsInTable(jdbcTemplate, "GROUPS") - 1;

		groupDao.delete(3);

		int actualRow = countRowsInTable(jdbcTemplate, "GROUPS");
		assertEquals(expectedRows, actualRow);
	}

	@Test
	public void givenFindGroupByIdNotExist_whenFindGroupById_thenGetObjectNotFoundException() {
		Optional<Group> actual = groupDao.findById(200);

		assertThat(actual).isEmpty();
	}

	@Test
	public void givenFindGroupById_whenFindGroupById_thenGroupWasFound() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add((new Course(3, "Chemistry", "Chem")));
		courses.add((new Course(4, "Physics", "Phy")));
		List<Student> students = new ArrayList<>();
		students.add(new Student(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses));
		Group expected = new Group(2, "b2b2", students);
		
		Optional<Group> actual = groupDao.findById(2);
		
		assertThat(actual).isPresent().get().isEqualTo(expected);
	}

	@Test
	public void givenFindAll_whenFindAllGroups_whenAllGroupWasFound() {
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
		Group expected = new Group(2, "b2b2", students);

		Optional<Group> actual = groupDao.findByStudentId(2);

		assertThat(actual).get().isEqualTo(expected);
	}
	
	@Test
	public void givenGroupIdAndCourseId_whenFindGroupByGroupIdAndCourseId_thenGetGroup() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add((new Course(3, "Chemistry", "Chem")));
		courses.add((new Course(4, "Physics", "Phy")));
		List<Student> students = new ArrayList<>();
		students.add(new Student(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", courses));
		Group expected = new Group(2, "b2b2", students);
		
		Optional<Group> actual = groupDao.findByGroupIdAndCourseId(2, 1);

		assertThat(actual).get().isEqualTo(expected);
	}
}
