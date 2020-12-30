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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import ru.stepev.config.TestConfig;
import ru.stepev.model.Course;
import ru.stepev.model.Gender;
import ru.stepev.model.Student;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
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
		Student student = new Student(29, 2280, "Victoria", "Semenova", LocalDate.of(2020, 9, 1), "Semenova@mail.ru",
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
		coursesForStudent.add((new Course(5, "Informatica", "Info")));
		Student expected = new Student(2, 124, "Ivan", "Petrov", LocalDate.of(2020, 9, 4), "webIP@mail.ru", Gender.MALE,
				"City18", coursesForStudent);

		Optional<Student> actual = studentDao.findById(2);

		assertThat(actual).get().isEqualTo(expected);
	}

	@Test
	public void givenFindAllStudents_whenFindAllStudents_thenAllStudentsWereFound() {

		List<Student> actual = studentDao.findAll();
		AtomicInteger count = new AtomicInteger(0);
		actual.stream().filter(s->!s.equals(expectedStudents.get(count.getAndIncrement()))).forEach(System.out::println);

		assertThat(expectedStudents).isEqualTo(actual);
	}

	@Test
	public void givenFindStudentByFirstAndLastNames_whenFindStudent_thenStudentWasFound() {
		List<Course> coursesForStudent = new ArrayList<>();
		coursesForStudent.add(new Course(1, "Mathematics", "Math"));
		coursesForStudent.add(new Course(2, "Biology", "Bio"));
		coursesForStudent.add((new Course(3, "Chemistry", "Chem")));
		coursesForStudent.add((new Course(4, "Physics", "Phy")));
		coursesForStudent.add((new Course(5, "Informatica", "Info")));
		List<Student> expected = new ArrayList<>();
		expected.add(new Student(10, 2125, "Peter", "Zlobin", LocalDate.of(2020, 9, 5), "web2PI@mail.ru", Gender.MALE,
				"City19", coursesForStudent));

		List<Student> actual = studentDao.findByFirstAndLastNames("Peter", "Zlobin");

		assertThat(expected).isEqualTo(actual);
	}
	
	@Test
	public void findNumberOfItems_whenfindNumberOfItems_thenGetCorrectNumberOfStudents() {
		int expected = 29;
		
		int actual = studentDao.findNumberOfItems();
		
		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void findAndSortByFirstName_whenFindAndSortByFirstName_thenGetCorrectSortedListStudentsByFirstName() {
		
		List<Student> actual = studentDao.findAndSortByFirstName(2, 1);
		
		assertThat(actual).isEqualTo(studentsSortedByFirstNAme);
	}

	@Test
	public void findAndSortByLastName_whenFindAndSortByLastName_thenGetCorrectSortedListStudentsByLastName() {
		
		List<Student> actual = studentDao.findAndSortByLastName(2, 1);
		
		assertThat(actual).isEqualTo(expectedStudentsSortedByLastName);
	}

	@Test
	public void findAndSortById_whenFindAndSortById_thenGetCorrectSortedListStudentsById() {
		
		List<Student> actual = studentDao.findAndSortById(2, 0);
		
		assertThat(actual).isEqualTo(expectedStudentsSortedById);
	}

	@Test
	public void findAndSortByBirthday_whenFindAndSortByBirthday_thenGetCorrectSortedListStudentsByBirthday() {
		
		List<Student> actual = studentDao.findAndSortByBirthday(2, 1);
		
		assertThat(actual).isEqualTo(expectedStudentsSortedByBirthday);
	}

	@Test
	public void findAndSortByEmail_whenFindAndSortByEmail_thenGetCorrectSortedListStudentsByEmail() {
	
		List<Student> actual = studentDao.findAndSortByEmail(2, 1);
		
		assertThat(actual).isEqualTo(expectedStudentsSortedByMail);
	}

	@Test
	public void findAndSortByAddress_whenFindAndSortByAddress_thenGetCorrectSortedListStudentsByAddress() {
		
		List<Student> actual = studentDao.findAndSortByAddress(2, 1);
		
		assertThat(actual).isEqualTo(expectedStudentsSortedByAddress);
	}
}
