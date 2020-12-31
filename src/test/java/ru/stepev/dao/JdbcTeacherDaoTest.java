package ru.stepev.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.*;
import static ru.stepev.data.DataTest.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.jdbc.core.JdbcTemplate;

import ru.stepev.config.TestConfig;
import ru.stepev.model.Course;
import ru.stepev.model.Gender;
import ru.stepev.model.Student;
import ru.stepev.model.Teacher;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class JdbcTeacherDaoTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private TeacherDao teacherDao;

	@Test
	public void givenCreateTeacher_whenCreateTeacher_thenTeacherWasCreated() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		Teacher teacher = new Teacher(12, 428, "Victoria", "Semenova", LocalDate.of(2020, 9, 1), "Semenova@mail.ru",
				Gender.FEMALE, "City10", courses);
		String inquiry = String.format(
				"teacher_id = %d AND course_id = %d OR teacher_id = %d AND course_id = %d OR teacher_id = %d AND course_id = %d OR teacher_id = %d AND course_id = %d",
				teacher.getId(), 1, teacher.getId(), 2, teacher.getId(), 3, teacher.getId(), 4);
		int expectedRows = countRowsInTable(jdbcTemplate, "TEACHERS") + 1;
		int expectedRowInTeachersCourses = countRowsInTableWhere(jdbcTemplate, "TEACHERS_COURSES", inquiry) + 4;

		teacherDao.create(teacher);

		int actualRows = countRowsInTable(jdbcTemplate, "TEACHERS");
		assertEquals(expectedRows, actualRows);
		int actualRowInTeachersCourses = countRowsInTableWhere(jdbcTemplate, "TEACHERS_COURSES", inquiry);
		assertEquals(expectedRowInTeachersCourses, actualRowInTeachersCourses);
	}

	@Test
	public void givenUpdateTeacher_whenUpdateTeacherById_thenTeacherWasUpdated() {
		List<Course> coursesForTeacher = new ArrayList<>();
		coursesForTeacher.add((new Course(3, "Chemistry", "Chem")));
		coursesForTeacher.add((new Course(4, "Physics", "Phy")));
		Teacher teacher = new Teacher(2, 228, "Victoria", "Semenova", LocalDate.of(2020, 9, 1), "Semenova@mail.ru",
				Gender.FEMALE, "City10", coursesForTeacher);
		String inquiry = String.format(
				"id = %d AND first_name = '%s' AND last_name = '%s' AND birthday = '%s' AND "
						+ "email = '%s' AND gender = '%s' AND address = '%s'",
				teacher.getId(), teacher.getFirstName(), teacher.getLastName(), teacher.getBirthday(),
				teacher.getEmail(), teacher.getGender().toString(), teacher.getAddress());
		String inquiryForTeachersCourses = String.format(
				"teacher_id = %d AND course_id = %d OR teacher_id = %d AND course_id = %d OR teacher_id = %d AND course_id = %d OR teacher_id = %d AND course_id = %d",
				teacher.getId(), 1, teacher.getId(), 2, teacher.getId(), 3, teacher.getId(), 4);
		int expectedRows = countRowsInTableWhere(jdbcTemplate, "TEACHERS", inquiry) + 1;
		int expectedRowInTeachersCourses = countRowsInTableWhere(jdbcTemplate, "TEACHERS_COURSES",
				inquiryForTeachersCourses) - 2;

		teacherDao.update(teacher);

		int actualRows = countRowsInTableWhere(jdbcTemplate, "TEACHERS", inquiry);
		assertEquals(expectedRows, actualRows);

		int actualRowInTeachersCourses = countRowsInTableWhere(jdbcTemplate, "TEACHERS_COURSES",
				inquiryForTeachersCourses);
		assertEquals(expectedRowInTeachersCourses, actualRowInTeachersCourses);
	}

	@Test
	public void givenDelete_whenDeleteTeacherById_thenTeacherWasDeleted() {
		int expectedRows = countRowsInTable(jdbcTemplate, "TEACHERS") - 1;

		teacherDao.delete(4);

		int actualRows = countRowsInTable(jdbcTemplate, "TEACHERS");
		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void givenFindTeacherByIdNotExist_whenNotFindTeacherById_thenGetEmptyOptional() {
		Optional<Teacher> actual = teacherDao.findById(200);

		assertThat(actual).isEmpty();
	}

	@Test
	public void givenFindTeacherById_whenFindTeacherById_thenTheacherWasFound() {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		Teacher expected = new Teacher(3, 125, "Peter", "Ivanov", LocalDate.of(2020, 9, 5), "webPI@mail.ru",
				Gender.MALE, "City19", courses);

		Optional<Teacher> actual = teacherDao.findById(3);

		assertThat(actual).get().isEqualTo(expected);
	}

	@Test
	public void givenFindAllTeachers_whenFindAllTeachers_thenAllTeachersWereFound() {

		List<Teacher> actual = teacherDao.findAll();
		
		AtomicInteger count = new AtomicInteger(0);
		
		actual.stream().filter(t->!t.equals(expectedTeachers.get(count.getAndIncrement()))).forEach(System.out::println);

		assertThat(expectedTeachers).isEqualTo(actual);
	}

	@Test
	public void findNumberOfItems_whenfindNumberOfItems_thenGetCorrectNumberOfTeachers() {
		int expected = 12;

		int actual = teacherDao.findNumberOfItems();

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void findAndSortByFirstName_whenFindAndSortByFirstName_thenGetCorrectSortedListTeachersByFirstName() {

		List<Teacher> actual = teacherDao.findAndSortByFirstName(2, 1);

		assertThat(actual).isEqualTo(expectedTeachersSortedByFirstName);
	}

	@Test
	public void findAndSortByLastName_whenFindAndSortByLastName_thenGetCorrectSortedListTeachersByLastName() {

		List<Teacher> actual = teacherDao.findAndSortByLastName(2, 1);

		assertThat(actual).isEqualTo(expectedTeachersSortedByLastName);
	}

	@Test
	public void findAndSortById_whenFindAndSortById_thenGetCorrectSortedListTeachersById() {

		List<Teacher> actual = teacherDao.findAndSortById(2, 1);

		assertThat(actual).isEqualTo(expectedTeachersSortedById);
	}

	@Test
	public void findAndSortByBirthday_whenFindAndSortByBirthday_thenGetCorrectSortedListTeachersByBirthday() {

		List<Teacher> actual = teacherDao.findAndSortByBirthday(2, 1);

		assertThat(actual).isEqualTo(expectedTeachersSortedByBirthday);
	}

	@Test
	public void findAndSortByEmail_whenFindAndSortByEmail_thenGetCorrectSortedListTeachersByEmail() {

		List<Teacher> actual = teacherDao.findAndSortByEmail(2, 1);

		assertThat(actual).isEqualTo(expectedTeachersSortedByEmail);
	}

	@Test
	public void findAndSortByAddress_whenFindAndSortByAddress_thenGetCorrectSortedListTeachersByAddress() {

		List<Teacher> actual = teacherDao.findAndSortByAddress(2, 1);

		assertThat(actual).isEqualTo(expectedTeachersSortedByAddress);
	}
}
