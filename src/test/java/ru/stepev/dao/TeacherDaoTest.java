package ru.stepev.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
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
import ru.stepev.model.Gender;
import ru.stepev.model.Teacher;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class TeacherDaoTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private TeacherDao teacherDao;

	@Test
	public void create_whenCreateOneTeacher_thenTableTeachersMustHaveCorrectCountOfRows() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));

		Teacher teacher = new Teacher(6, 228, "Victoria", "Semenova", LocalDate.parse("2020-09-01"), "Semenova@mail.ru",
				Gender.MALE, "City10", courses);
		String inquiry = String.format(
				"teacher_id = %d AND course_id = %d OR teacher_id = %d AND course_id = %d OR teacher_id = %d AND course_id = %d OR teacher_id = %d AND course_id = %d",
				teacher.getId(), 1, teacher.getId(), 2, teacher.getId(), 3, teacher.getId(), 4);

		int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "TEACHERS") + 1;
		int expectedRowInTeachersCourses = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "TEACHERS_COURSES",
				inquiry) + 4;

		teacherDao.create(teacher);
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "TEACHERS");
		assertEquals(expectedRows, actualRows);

		int actualRowInTeachersCourses = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "TEACHERS_COURSES", inquiry);
		assertEquals(expectedRowInTeachersCourses, actualRowInTeachersCourses);

	}

	@Test
	public void update_whenUpdateTeacherById_thenTableMustHaveCorrectRow() throws Exception {
		List<Course> coursesForTeacher = new ArrayList<>();
		coursesForTeacher.add((new Course(3, "Chemistry", "Chem")));
		coursesForTeacher.add((new Course(4, "Physics", "Phy")));
		Teacher teacher = new Teacher(2, 228, "Victoria", "Semenova", LocalDate.parse("2020-09-01"), "Semenova@mail.ru",
				Gender.MALE, "City10", coursesForTeacher);
		String inquiry = String.format(
				"id = %d AND first_name = '%s' AND last_name = '%s' AND birthday = '%s' AND "
						+ "email = '%s' AND gender = '%s' AND address = '%s'",
				teacher.getId(), teacher.getFirstName(), teacher.getLastName(), teacher.getBirthday().toString(),
				teacher.getEmail(), teacher.getGender().toString(), teacher.getAddress());
		String inquiryForTeachersCourses = String.format(
				"teacher_id = %d AND course_id = %d OR teacher_id = %d AND course_id = %d OR teacher_id = %d AND course_id = %d OR teacher_id = %d AND course_id = %d",
				teacher.getId(), 1, teacher.getId(), 2, teacher.getId(), 3, teacher.getId(), 4);
		int expectedRows = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "TEACHERS", inquiry) + 1;
		int expectedRowInTeachersCourses = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "TEACHERS_COURSES",
				inquiryForTeachersCourses) - 2;

		teacherDao.update(teacher);

		int actualRows = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "TEACHERS", inquiry);
		assertEquals(expectedRows, actualRows);

		int actualRowInTeachersCourses = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "TEACHERS_COURSES",
				inquiryForTeachersCourses);
		assertEquals(expectedRowInTeachersCourses, actualRowInTeachersCourses);
	}

	@Test
	public void delete_whenDeleteTeacherById_thenCountOfRowsInTableMustDecrease() throws Exception {
		int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "TEACHERS") - 1;
		teacherDao.delete(4);
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "TEACHERS");
		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void findTeacherById_shouldFindCorrectTeacherById_thenTableHaveOne() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		Teacher expected = new Teacher(3, 125, "Peter", "Ivanov", "2020-09-05", "webPI@mail.ru", "FEMALE", "City19",
				courses);

		Teacher actual = teacherDao.findById(3);
		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void findAllTeachers_shouldFindAllTeachers_whenTableHaveMoreThenOne() throws Exception {
		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));

		List<Course> coursesForUpdatedTeacher = new ArrayList<>();
		coursesForUpdatedTeacher.add((new Course(3, "Chemistry", "Chem")));
		coursesForUpdatedTeacher.add((new Course(4, "Physics", "Phy")));

		List<Teacher> actual = teacherDao.findAll();

		List<Teacher> expected = new ArrayList<>();
		Teacher teacher = new Teacher(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17",
				courses);
		expected.add(teacher);

		teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", courses);
		expected.add(teacher);

		teacher = new Teacher(3, 125, "Peter", "Ivanov", "2020-09-05", "webPI@mail.ru", "FEMALE", "City19", courses);
		expected.add(teacher);

		teacher = new Teacher(5, 227, "Irina", "Stepanova", "2020-09-07", "Ivanov@mail.ru", "FEMALE", "City11",
				courses);
		expected.add(teacher);

		assertThat(expected).isEqualTo(actual);
	}
}
