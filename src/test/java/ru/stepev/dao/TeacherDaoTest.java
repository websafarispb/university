package ru.stepev.dao;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
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
	
	private static List<Course> courses;

	@BeforeAll
	public static void init() {
		courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
	}

	@Test
	public void createOneTeacherTest() throws Exception {
		List<Course> coursesForTeacher = new ArrayList<>();
		coursesForTeacher.add((new Course(3, "Chemistry", "Chem")));
		coursesForTeacher.add((new Course(4, "Physics", "Phy")));
		Teacher teacher = new Teacher(6, 228, "Victoria", "Semenova", LocalDate.parse("2020-09-01"), "Semenova@mail.ru",
				Gender.MALE, "City10", coursesForTeacher);
		teacherDao.create(teacher);
		int expectedRows = 6;
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "TEACHERS");
		assertEquals(expectedRows, actualRows);

		int expectedRowInTeachersCourses = 2;
		int actualRowInTeachersCourses = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "TEACHERS_COURSES",
				String.format("teacher_id = %d AND course_id = %d OR teacher_id = %d AND course_id = %d",
						teacher.getId(), 3, teacher.getId(), 4));
		assertEquals(expectedRowInTeachersCourses, actualRowInTeachersCourses);

	}

	@Test
	public void updateTeacherByIdTest() throws Exception {
		List<Course> coursesForTeacher = new ArrayList<>();
		coursesForTeacher.add((new Course(3, "Chemistry", "Chem")));
		coursesForTeacher.add((new Course(4, "Physics", "Phy")));
		Teacher teacher = new Teacher(2, 228, "Victoria", "Semenova", LocalDate.parse("2020-09-01"), "Semenova@mail.ru",
				Gender.MALE, "City10", coursesForTeacher);
		teacherDao.update(teacher);
		int expectedRows = 1;
		int actualRows = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "TEACHERS",
				String.format(
						"id = %d AND first_name = '%s' AND last_name = '%s' AND birthday = '%s' AND "
								+ "email = '%s' AND gender = '%s' AND address = '%s'",
						teacher.getId(), teacher.getFirstName(), teacher.getLastName(),
						teacher.getBirthday().toString(), teacher.getEmail(), teacher.getGender().toString(),
						teacher.getAddress()));
		assertEquals(expectedRows, actualRows);

		int expectedRowInTeachersCourses = 2;
		int actualRowInTeachersCourses = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "TEACHERS_COURSES",
				String.format("teacher_id = %d AND course_id = %d OR teacher_id = %d AND course_id = %d",
						teacher.getId(), 3, teacher.getId(), 4));
		assertEquals(expectedRowInTeachersCourses, actualRowInTeachersCourses);
	}

	@Test
	public void deleteTeacherByIdTest() throws Exception {
		int expectedRows = 5;
		teacherDao.delete(2);
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "TEACHERS");
		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void findTeacherByIdTest() throws Exception {
		List<Course> coursesForTeacher = new ArrayList<>();
		coursesForTeacher.add(new Course(1, "Mathematics", "Math"));
		coursesForTeacher.add(new Course(2, "Biology", "Bio"));
		coursesForTeacher.add((new Course(3, "Chemistry", "Chem")));
		coursesForTeacher.add((new Course(4, "Physics", "Phy")));
		Teacher expected = new Teacher(3, 125, "Peter", "Ivanov", "2020-09-05", "webPI@mail.ru", "FEMALE", "City19", courses);
		expected.setCourses(coursesForTeacher);
		Teacher actual = teacherDao.findById(3);
		assertEquals(expected, actual);
	}

	@Test
	public void findAllTeachersInBasaTest() throws Exception {
		List<Teacher> actual = teacherDao.findAllTeacher();

		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));

		List<Teacher> expected = new ArrayList<>();
		Teacher teacher = new Teacher(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17", courses);
		teacher.setCourses(courses);
		expected.add(teacher);

		List<Course> coursesTwo = new ArrayList<>();
		coursesTwo.add(new Course(3, "Chemistry", "Chem"));
		coursesTwo.add(new Course(4, "Physics", "Phy"));

		teacher = new Teacher(2, 124, "Victoria", "Semenova", "2020-09-01", "Semenova@mail.ru", "MALE", "City10", courses);
		teacher.setCourses(coursesTwo);
		expected.add(teacher);

		teacher = new Teacher(3, 125, "Peter", "Ivanov", "2020-09-05", "webPI@mail.ru", "FEMALE", "City19", courses);
		teacher.setCourses(courses);
		expected.add(teacher);

		teacher = new Teacher(4, 126, "Peter", "Smirnov", "2020-09-06", "webPS@mail.ru", "MALE", "City17", courses);
		teacher.setCourses(courses);
		expected.add(teacher);

		teacher = new Teacher(5, 227, "Irina", "Stepanova", "2020-09-07", "Ivanov@mail.ru", "FEMALE", "City11", courses);
		teacher.setCourses(courses);
		expected.add(teacher);

		assertEquals(expected, actual);
	}

	@Test
	public void findAllCourseOfTeacherTest() {
		int expectedRows = 4;
		int actualRows = teacherDao.findAllCourseOfTeacher(1).size();
		assertEquals(expectedRows, actualRows);
	}

}
