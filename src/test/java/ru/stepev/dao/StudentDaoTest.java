package ru.stepev.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.IDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import ru.stepev.dao.rowmapper.StudentRowMapper;
import ru.stepev.model.Course;
import ru.stepev.model.Student;

public class StudentDaoTest {

	private static IDatabaseTester databaseTester;
	private static JdbcTemplate jdbcTemplate;
	private static StudentDao studentDao;

	@BeforeAll
	public static void init() throws Exception {
		DataSource dataSourse = new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(EmbeddedDatabaseType.H2)
				.setScriptEncoding("UTF-8").ignoreFailedDrops(true).addScript("schema.sql").addScript("data.sql")
				.build();
		jdbcTemplate = new JdbcTemplate(dataSourse);
		DataSourceDatabaseTester dataSourceDatabaseTester = new DataSourceDatabaseTester(dataSourse);
		databaseTester = dataSourceDatabaseTester;

		StudentRowMapper studentRowMapper = new StudentRowMapper();
		studentDao = new StudentDao(jdbcTemplate, studentRowMapper);
	}

	@Test
	public void createOneStudentTest() throws Exception {

		List<Student> expected = new ArrayList<>();
		expected.add(new Student(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17"));
		expected.add(new Student(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18"));
		expected.add(new Student(3, 125, "Peter", "Ivanov", "2020-09-05", "webPI@mail.ru", "FEMALE", "City19"));
		expected.add(new Student(5, 227, "Irina", "Stepanova", "2020-09-07", "Ivanov@mail.ru", "FEMALE", "City11"));
		expected.add(new Student(6, 228, "Victoria", "Semenova", "2020-09-01", "Semenova@mail.ru", "FEMALE", "City10"));

		studentDao.create(
				new Student(6, 228, "Victoria", "Semenova", "2020-09-01", "Semenova@mail.ru", "FEMALE", "City10"));

		IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("STUDENTS");

		List<Student> actual = new ArrayList<>();
		for (int i = 0; i < actualTable.getRowCount(); i++) {
			actual.add(new Student(Integer.parseInt(actualTable.getValue(i, "student_id").toString()),
					Integer.parseInt(actualTable.getValue(i, "personal_number").toString()),
					actualTable.getValue(i, "first_name").toString(), actualTable.getValue(i, "last_name").toString(),
					actualTable.getValue(i, "birthday").toString(), actualTable.getValue(i, "email").toString(),
					actualTable.getValue(i, "gender").toString(), actualTable.getValue(i, "address").toString()));
		}
		assertEquals(expected, actual);
	}

	@Test
	public void updateStudentByIdTest() throws Exception {

		List<Student> expected = new ArrayList<>();
		expected.add(new Student(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17"));
		expected.add(new Student(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18"));
		expected.add(new Student(3, 125, "Igor", "Smirnov", "2020-09-03", "webIS@mail.ru", "MALE", "City17"));
		expected.add(new Student(5, 227, "Irina", "Stepanova", "2020-09-07", "Ivanov@mail.ru", "FEMALE", "City11"));
		expected.add(new Student(6, 228, "Victoria", "Semenova", "2020-09-01", "Semenova@mail.ru", "FEMALE", "City10"));

		studentDao.update(new Student(3, 125, "Igor", "Smirnov", "2020-09-03", "webIS@mail.ru", "MALE", "City17"), 3);
		List<Student> actual = studentDao.findAllStudents();

		assertEquals(expected, actual);
	}

	@Test
	public void deleteStudentByIdTest() throws Exception {

		List<Student> expected = new ArrayList<>();
		expected.add(new Student(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17"));
		expected.add(new Student(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18"));
		expected.add(new Student(3, 125, "Peter", "Ivanov", "2020-09-05", "webPI@mail.ru", "FEMALE", "City19"));
		expected.add(new Student(5, 227, "Irina", "Stepanova", "2020-09-07", "Ivanov@mail.ru", "FEMALE", "City11"));

		studentDao.delete(4);
		List<Student> actual = studentDao.findAllStudents();

		assertEquals(expected, actual);
	}

	@Test
	public void findStudentByIdTest() throws Exception {

		Student expected = new Student(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		Student actual = studentDao.findById(2);

		assertEquals(expected, actual);
	}

	@Test
	public void findAllStudentsInBasaTest() throws Exception {
		List<Student> actual = studentDao.findAllStudents();

		List<Student> expected = new ArrayList<>();
		expected.add(new Student(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17"));
		expected.add(new Student(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18"));
		expected.add(new Student(3, 125, "Peter", "Ivanov", "2020-09-05", "webPI@mail.ru", "FEMALE", "City19"));
		expected.add(new Student(4, 126, "Peter", "Smirnov", "2020-09-06", "webPS@mail.ru", "MALE", "City17"));
		expected.add(new Student(5, 227, "Irina", "Stepanova", "2020-09-07", "Ivanov@mail.ru", "FEMALE", "City11"));

		assertEquals(expected, actual);
	}

	@Test
	public void assignStudentToCourseTest() throws Exception {
		Student student = new Student(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");

		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		student.setCourses(courses);

		List<Integer> studentAndCoursesExpected = Arrays.asList(2, 1, 2, 2, 2, 3, 2, 4);
		studentDao.assignToCourse(student);

		IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("STUDENTS_COURSES");

		List<Integer> studentAndCoursesActual = new ArrayList<>();
		for (int i = 0; i < actualTable.getRowCount(); i++) {
			studentAndCoursesActual.add(Integer.parseInt(actualTable.getValue(i, "student_id").toString()));
			studentAndCoursesActual.add(Integer.parseInt(actualTable.getValue(i, "course_id").toString()));
		}
		assertEquals(studentAndCoursesExpected, studentAndCoursesActual);
	}

	@Test
	public void findStudentByFirstAndLastNamesTest() throws Exception {
		List<Student> expected = new ArrayList<>();
		expected.add(new Student(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17"));

		List<Student> actual = studentDao.findStudentByFirstAndLastNames("Peter", "Petrov");
		assertEquals(expected, actual);
	}

	@AfterAll
	public static void cleanUp() throws Exception {
		databaseTester.onTearDown();
		databaseTester = null;
	}

}
