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

import ru.stepev.dao.rowmapper.TeacherRowMapper;
import ru.stepev.model.Course;
import ru.stepev.model.Teacher;

public class TeacherDaoTest {

	private static IDatabaseTester databaseTester;
	private static JdbcTemplate jdbcTemplate;
	private static TeacherDao teacherDao;

	@BeforeAll
	public static void init() throws Exception {
		DataSource dataSourse = new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(EmbeddedDatabaseType.H2)
				.setScriptEncoding("UTF-8").ignoreFailedDrops(true).addScript("schema.sql").addScript("data.sql")
				.build();
		jdbcTemplate = new JdbcTemplate(dataSourse);
		DataSourceDatabaseTester dataSourceDatabaseTester = new DataSourceDatabaseTester(dataSourse);
		databaseTester = dataSourceDatabaseTester;

		TeacherRowMapper teacherRowMapper = new TeacherRowMapper();
		teacherDao = new TeacherDao(jdbcTemplate, teacherRowMapper);
	}

	@Test
	public void createOneTeacherTest() throws Exception {

		List<Teacher> expected = new ArrayList<>();
		expected.add(new Teacher(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17"));
		expected.add(new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18"));
		expected.add(new Teacher(3, 125, "Igor", "Smirnov", "2020-09-03", "webIS@mail.ru", "MALE", "City17"));
		expected.add(new Teacher(4, 126, "Peter", "Smirnov", "2020-09-06", "webPS@mail.ru", "MALE", "City17"));
		expected.add(new Teacher(5, 227, "Irina", "Stepanova", "2020-09-07", "Ivanov@mail.ru", "FEMALE", "City11"));
		expected.add(new Teacher(6, 228, "Victoria", "Semenova", "2020-09-01", "Semenova@mail.ru", "FEMALE", "City10"));

		teacherDao.create(
				new Teacher(6, 228, "Victoria", "Semenova", "2020-09-01", "Semenova@mail.ru", "FEMALE", "City10"));

		IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("TEACHERS");

		List<Teacher> actual = new ArrayList<>();
		for (int i = 0; i < actualTable.getRowCount(); i++) {
			actual.add(new Teacher(Integer.parseInt(actualTable.getValue(i, "teacher_id").toString()),
					Integer.parseInt(actualTable.getValue(i, "personal_number").toString()),
					actualTable.getValue(i, "first_name").toString(), actualTable.getValue(i, "last_name").toString(),
					actualTable.getValue(i, "birthday").toString(), actualTable.getValue(i, "email").toString(),
					actualTable.getValue(i, "gender").toString(), actualTable.getValue(i, "address").toString()));
		}
		assertEquals(expected, actual);
	}

	@Test
	public void updateTeacherByIdTest() throws Exception {

		List<Teacher> expected = new ArrayList<>();
		expected.add(new Teacher(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17"));
		expected.add(new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18"));
		expected.add(new Teacher(3, 125, "Igor", "Smirnov", "2020-09-03", "webIS@mail.ru", "MALE", "City17"));
		expected.add(new Teacher(4, 126, "Peter", "Smirnov", "2020-09-06", "webPS@mail.ru", "MALE", "City17"));
		expected.add(new Teacher(5, 227, "Irina", "Stepanova", "2020-09-07", "Ivanov@mail.ru", "FEMALE", "City11"));

		teacherDao.update(new Teacher(3, 125, "Igor", "Smirnov", "2020-09-03", "webIS@mail.ru", "MALE", "City17"), 3);
		List<Teacher> actual = teacherDao.findAllTeacher();

		assertEquals(expected, actual);
	}

	@Test
	public void deleteTeacherByIdTest() throws Exception {

		List<Teacher> expected = new ArrayList<>();
		expected.add(new Teacher(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17"));
		expected.add(new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18"));
		expected.add(new Teacher(3, 125, "Igor", "Smirnov", "2020-09-03", "webIS@mail.ru", "MALE", "City17"));
		expected.add(new Teacher(5, 227, "Irina", "Stepanova", "2020-09-07", "Ivanov@mail.ru", "FEMALE", "City11"));
		expected.add(new Teacher(6, 228, "Victoria", "Semenova", "2020-09-01", "Semenova@mail.ru", "FEMALE", "City10"));

		teacherDao.delete(4);
		List<Teacher> actual = teacherDao.findAllTeacher();

		assertEquals(expected, actual);
	}

	@Test
	public void findTeacherByIdTest() throws Exception {

		Teacher expected = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");
		Teacher actual = teacherDao.findById(2);

		assertEquals(expected, actual);
	}

	@Test
	public void findAllTeachersInBasaTest() throws Exception {
		List<Teacher> actual = teacherDao.findAllTeacher();

		List<Teacher> expected = new ArrayList<>();
		expected.add(new Teacher(1, 123, "Peter", "Petrov", "2020-09-03", "webPP@mail.ru", "MALE", "City17"));
		expected.add(new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18"));
		expected.add(new Teacher(3, 125, "Igor", "Smirnov", "2020-09-03", "webIS@mail.ru", "MALE", "City17"));
		expected.add(new Teacher(4, 126, "Peter", "Smirnov", "2020-09-06", "webPS@mail.ru", "MALE", "City17"));
		expected.add(new Teacher(5, 227, "Irina", "Stepanova", "2020-09-07", "Ivanov@mail.ru", "FEMALE", "City11"));

		assertEquals(expected, actual);
	}

	@Test
	public void assignTeacherToCourseTest() throws Exception {
		Teacher teacher = new Teacher(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18");

		List<Course> courses = new ArrayList<>();
		courses.add(new Course(1, "Mathematics", "Math"));
		courses.add(new Course(2, "Biology", "Bio"));
		courses.add(new Course(3, "Chemistry", "Chem"));
		courses.add(new Course(4, "Physics", "Phy"));
		teacher.setCourses(courses);

		List<Integer> teacherAndCoursesExpected = Arrays.asList(2, 1, 2, 2, 2, 3, 2, 4);
		teacherDao.assignToCourse(teacher);

		IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("TEACHERS_COURSES");

		List<Integer> teacherAndCoursesActual = new ArrayList<>();
		for (int i = 0; i < actualTable.getRowCount(); i++) {
			teacherAndCoursesActual.add(Integer.parseInt(actualTable.getValue(i, "teacher_id").toString()));
			teacherAndCoursesActual.add(Integer.parseInt(actualTable.getValue(i, "course_id").toString()));
		}
		assertEquals(teacherAndCoursesExpected, teacherAndCoursesActual);
	}

	@AfterAll
	public static void cleanUp() throws Exception {
		databaseTester.onTearDown();
		databaseTester = null;
	}

}
