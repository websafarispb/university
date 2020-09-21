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
import ru.stepev.model.Student;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class StudentDaoTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private StudentDao studentDao;

	public static List<Course> coursesForStudent = new ArrayList<>();

	@BeforeAll
	public static void init() {
		coursesForStudent.add(new Course(1, "Mathematics", "Math"));
		coursesForStudent.add(new Course(2, "Biology", "Bio"));
		coursesForStudent.add((new Course(3, "Chemistry", "Chem")));
		coursesForStudent.add((new Course(4, "Physics", "Phy")));
	}

	@Test
	public void createOneStudentTest() throws Exception {
		int expectedRows = 5;
		Student student = new Student(6, 228, "Victoria", "Semenova", LocalDate.parse("2020-09-01"), "Semenova@mail.ru",
				Gender.FEMALE, "City10", coursesForStudent);
		studentDao.create(student);
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "STUDENTS");
		assertEquals(expectedRows, actualRows);

		int expected = 1;
		int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "STUDENTS", String.format(
				"id = %d AND first_name = '%s' AND last_name = '%s' "
						+ "AND  birthday = '%s' AND email = '%s' AND gender = '%s' AND address = '%s' ",
				student.getId(), student.getFirstName(), student.getLastName(), student.getBirthday().toString(),
				student.getEmail(), student.getGender(), student.getAddress()));
		assertEquals(expected, actual);
		int expectedRowInStudentsCourses = 2;
		int actualRowInStudentsCourses = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "STUDENTS_COURSES",
				String.format("student_id = %d AND course_id = %d OR student_id = %d AND course_id = %d",
						student.getId(), 3, student.getId(), 4));
		assertEquals(expectedRowInStudentsCourses, actualRowInStudentsCourses);
	}

	@Test
	public void updateStudentByIdTest() throws Exception {
		Student student = new Student(3, 328, "Ivan", "Stepanov", "2019-09-01", "Stepanov@mail.ru", "MALE", "City11",
				coursesForStudent);
		studentDao.update(student);
		int expected = 1;
		int actual = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "STUDENTS", String.format(
				"id = %d AND first_name = '%s' AND last_name = '%s' "
						+ "AND  birthday = '%s' AND email = '%s' AND gender = '%s' AND address = '%s' ",
				student.getId(), student.getFirstName(), student.getLastName(), student.getBirthday().toString(),
				student.getEmail(), student.getGender(), student.getAddress()));
		assertEquals(expected, actual);
	}

	@Test
	public void deleteStudentByIdTest() throws Exception {
		int expectedRows = 5;
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "STUDENTS");
		studentDao.delete(4);
		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void findStudentByIdTest() throws Exception {
		Student expected = new Student(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", coursesForStudent);
		Student actual = studentDao.findById(2);
		assertEquals(expected, actual);
	}

	@Test
	public void findAllStudentsInBasaTest() throws Exception {
		List<Student> actual = studentDao.findAllStudents();

		List<Student> expected = new ArrayList<>();
		expected.add(new Student(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17", coursesForStudent));
		expected.add(new Student(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18", coursesForStudent));
		expected.add(new Student(3, 125, "Peter", "Ivanov", "2020-09-05", "webPI@mail.ru", "FEMALE", "City19", coursesForStudent));
		expected.add(new Student(4, 126, "Peter", "Smirnov", "2020-09-06", "webPS@mail.ru", "MALE", "City17", coursesForStudent));
		expected.add(new Student(5, 227, "Irina", "Stepanova", "2020-09-07", "Ivanov@mail.ru", "FEMALE", "City11", coursesForStudent));

		assertEquals(expected, actual);
	}

	@Test
	public void findStudentByFirstAndLastNamesTest() throws Exception {
		List<Student> expected = new ArrayList<>();
		expected.add(new Student(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17", coursesForStudent));

		List<Student> actual = studentDao.findStudentByFirstAndLastNames("Peter", "Petrov");
		assertEquals(expected, actual);
	}
}
