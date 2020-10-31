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
import ru.stepev.model.Student;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class JdbcStudentDaoTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private StudentDao studentDao;

	@Test
	public void givenCreateStudent_whenCreateStudent_thenStudentWasCreated() {
		List<Course> coursesForStudent = new ArrayList<>();
		coursesForStudent.add(new Course(1, "Mathematics", "Math"));
		coursesForStudent.add(new Course(2, "Biology", "Bio"));
		coursesForStudent.add((new Course(3, "Chemistry", "Chem")));
		coursesForStudent.add((new Course(4, "Physics", "Phy")));
		Student student = new Student(8, 2280, "Victoria", "Semenova", LocalDate.of(2020, 9, 1), "Semenova@mail.ru",
				Gender.FEMALE, "City10", coursesForStudent);
		String inquryForOneStudent = String.format(
				"id = %d AND first_name = '%s' AND last_name = '%s' "
						+ "AND  birthday = '%s' AND email = '%s' AND gender = '%s' AND address = '%s' ",
				student.getId(), student.getFirstName(), student.getLastName(), student.getBirthday(),
				student.getEmail(), student.getGender(), student.getAddress());
		String inquryForStudentsCourses = String.format(
				"student_id = %d AND course_id = %d OR student_id = %d AND course_id = %d OR student_id = %d AND course_id = %d OR student_id = %d AND course_id = %d",
				student.getId(), 1, student.getId(), 2, student.getId(), 3, student.getId(), 4);
		int expectedRowsInStudentsTable = countRowsInTable(jdbcTemplate, "STUDENTS") + 1;
		int expectedCountRowsWichHaveOneStudent = countRowsInTableWhere(jdbcTemplate, "STUDENTS", inquryForOneStudent) + 1;
		int expectedRowInStudentsCourses = countRowsInTableWhere(jdbcTemplate, "STUDENTS_COURSES",
				inquryForStudentsCourses) + 4;

		studentDao.create(student);

		int actualRowsInStudentsTable = countRowsInTable(jdbcTemplate, "STUDENTS");
		assertEquals(expectedRowsInStudentsTable, actualRowsInStudentsTable);
		int actualCountRowsWichHaveOneStudent = countRowsInTableWhere(jdbcTemplate, "STUDENTS", inquryForOneStudent);
		assertEquals(expectedCountRowsWichHaveOneStudent, actualCountRowsWichHaveOneStudent);
		int actualRowInStudentsCourses = countRowsInTableWhere(jdbcTemplate, "STUDENTS_COURSES",
				inquryForStudentsCourses);
		assertEquals(expectedRowInStudentsCourses, actualRowInStudentsCourses);
	}

	@Test
	public void givenUpdateStudent_whenUpdateStudentById_thenStudentWasUpdated() {
		List<Course> coursesForStudent = new ArrayList<>();
		coursesForStudent.add(new Course(1, "Mathematics", "Math"));
		coursesForStudent.add(new Course(2, "Biology", "Bio"));
		coursesForStudent.add((new Course(3, "Chemistry", "Chem")));
		coursesForStudent.add((new Course(4, "Physics", "Phy")));
		Student student = new Student(3, 328, "Ivan", "Stepanov", LocalDate.of(2020, 9, 1), "Stepanov@mail.ru",
				Gender.MALE, "City11", coursesForStudent);
		String inqury = String.format(
				"id = %d AND first_name = '%s' AND last_name = '%s' "
						+ "AND  birthday = '%s' AND email = '%s' AND gender = '%s' AND address = '%s' ",
				student.getId(), student.getFirstName(), student.getLastName(), student.getBirthday().toString(),
				student.getEmail(), student.getGender(), student.getAddress());

		int expected = countRowsInTableWhere(jdbcTemplate, "STUDENTS", inqury) + 1;

		studentDao.update(student);

		int actual = countRowsInTableWhere(jdbcTemplate, "STUDENTS", inqury);
		assertEquals(expected, actual);
	}

	@Test
	public void givenDeleteStudent_whenDeleteStudentById_thenStudentWasDeleted() {
		int expectedRows = countRowsInTable(jdbcTemplate, "STUDENTS") - 1;

		studentDao.delete(4);

		int actualRows = countRowsInTable(jdbcTemplate, "STUDENTS");
		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void givenFindStudentByIdNotExist_whenFindStudentById_thenGetEmptyOptional() {
		Optional<Student> actual = studentDao.findById(200);

		assertThat(actual).isEmpty();
	}

	@Test
	public void givenFindStudentById_whenFindStudentById_thenStudentWasFound() {
		List<Course> coursesForStudent = new ArrayList<>();
		coursesForStudent.add(new Course(1, "Mathematics", "Math"));
		coursesForStudent.add(new Course(2, "Biology", "Bio"));
		coursesForStudent.add((new Course(3, "Chemistry", "Chem")));
		coursesForStudent.add((new Course(4, "Physics", "Phy")));
		Student expected = new Student(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", coursesForStudent);

		Optional<Student> actual = studentDao.findById(2);

		assertThat(actual).get().isEqualTo(expected);
	}

	@Test
	public void givenFindAllStudents_whenFindAllStudents_thenAllStudentsWereFound() {
		List<Course> coursesForStudent = new ArrayList<>();
		coursesForStudent.add(new Course(1, "Mathematics", "Math"));
		coursesForStudent.add(new Course(2, "Biology", "Bio"));
		coursesForStudent.add((new Course(3, "Chemistry", "Chem")));
		coursesForStudent.add((new Course(4, "Physics", "Phy")));
		List<Student> expected = new ArrayList<>();
		expected.add(new Student(1, 123, "Peter", "Petrov", LocalDate.of(2020, 9, 3), "webPP@mail.ru", Gender.MALE,
				"City17", coursesForStudent));
		expected.add(new Student(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", coursesForStudent));
		expected.add(new Student(3, 125, "Ivan", "Stepanov", LocalDate.of(2020, 9, 1), "Stepanov@mail.ru", Gender.MALE,
				"City11", coursesForStudent));
		expected.add(new Student(4, 126, "Peter", "Smirnov", LocalDate.of(2020, 9, 6), "webPS@mail.ru", Gender.MALE,
				"City17", coursesForStudent));
		expected.add(new Student(5, 227, "Irina", "Stepanova", LocalDate.of(2020, 9, 7), "Ivanov@mail.ru",
				Gender.FEMALE, "City11", coursesForStudent));
		expected.add(new Student(6, 527, "Daria", "Ivanova", LocalDate.of(2020, 9, 7), "Ivanova@mail.ru", Gender.FEMALE,
				"City20", coursesForStudent));
		expected.add(new Student(7, 528, "Igor", "Stepanov", LocalDate.of(2020, 9, 7), "Stepanov@mail.ru", Gender.MALE,
				"City20", coursesForStudent));

		List<Student> actual = studentDao.findAll();

		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void givenFindStudentByFirstAndLastNames_whenFindStudent_thenStudentWasFound() {
		List<Course> coursesForStudent = new ArrayList<>();
		coursesForStudent.add(new Course(1, "Mathematics", "Math"));
		coursesForStudent.add(new Course(2, "Biology", "Bio"));
		coursesForStudent.add((new Course(3, "Chemistry", "Chem")));
		coursesForStudent.add((new Course(4, "Physics", "Phy")));
		List<Student> expected = new ArrayList<>();
		expected.add(new Student(1, 123, "Peter", "Petrov", LocalDate.of(2020, 9, 3), "webPP@mail.ru", Gender.MALE,
				"City17", coursesForStudent));

		List<Student> actual = studentDao.findByFirstAndLastNames("Peter", "Petrov");

		assertThat(expected).isEqualTo(actual);
	}
}
