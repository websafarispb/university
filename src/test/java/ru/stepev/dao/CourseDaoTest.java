package ru.stepev.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
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

import ru.stepev.dao.CourseDao;
import ru.stepev.dao.rowmapper.CourseRowMapper;
import ru.stepev.model.Course;

public class CourseDaoTest {

	private static IDatabaseTester databaseTester;
	private static JdbcTemplate jdbcTemplate;
	private static CourseDao courseDao;

	@BeforeAll
	public static void init() throws Exception {
		DataSource dataSourse = new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(EmbeddedDatabaseType.H2)
				.setScriptEncoding("UTF-8").ignoreFailedDrops(true).addScript("schema.sql").addScript("data.sql")
				.build();
		jdbcTemplate = new JdbcTemplate(dataSourse);
		DataSourceDatabaseTester dataSourceDatabaseTester = new DataSourceDatabaseTester(dataSourse);
		databaseTester = dataSourceDatabaseTester; // (IDatabaseTester)

		CourseRowMapper courseRowMapper = new CourseRowMapper();
		courseDao = new CourseDao(jdbcTemplate, courseRowMapper);
	}

	@Test
	public void createOneCourseTest() throws Exception {

		List<Course> expected = new ArrayList<>();
		expected.add(new Course(1, "Mathematics", "Math"));
		expected.add(new Course(2, "Biology", "Bio"));
		expected.add(new Course(3, "Chemistry", "Chem"));
		expected.add(new Course(4, "History", "History description"));
		expected.add(new Course(5, "Geography", "Geo"));

		courseDao.create(new Course("Geography", "Geo"));

		IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("COURSES");

		List<Course> actual = new ArrayList<>();
		for (int i = 0; i < actualTable.getRowCount(); i++) {
			actual.add(new Course(Integer.parseInt(actualTable.getValue(i, "course_id").toString()),
					actualTable.getValue(i, "course_name").toString(),
					actualTable.getValue(i, "course_description").toString()));
		}
		assertEquals(expected, actual);
	}

	@Test
	public void updateCourseByIdTest() throws Exception {

		List<Course> expected = new ArrayList<>();
		expected.add(new Course(1, "Mathematics", "Math"));
		expected.add(new Course(2, "Biology", "Bio"));
		expected.add(new Course(3, "Chemistry", "Chem"));
		expected.add(new Course(4, "History", "History description"));

		courseDao.update(new Course(4, "History", "History description"), 4);
		List<Course> actual = courseDao.findAllCourses();

		assertEquals(expected, actual);
	}

	@Test
	public void deleteCourseByIdTest() throws Exception {

		List<Course> expected = new ArrayList<>();
		expected.add(new Course(1, "Mathematics", "Math"));
		expected.add(new Course(2, "Biology", "Bio"));
		expected.add(new Course(4, "History", "History description"));
		expected.add(new Course(5, "Geography", "Geo"));

		courseDao.delete(3);
		List<Course> actual = courseDao.findAllCourses();

		assertEquals(expected, actual);
	}

	@Test
	public void findCoyrseByIdTest() throws Exception {

		Course expected = new Course(2, "Biology", "Bio");
		Course actual = courseDao.findById(2);

		assertEquals(expected, actual);
	}

	@Test
	public void findAllCoursesInBasaTest() throws Exception {
		List<Course> actual = courseDao.findAllCourses();

		List<Course> expected = new ArrayList<>();
		expected.add(new Course(1, "Mathematics", "Math"));
		expected.add(new Course(2, "Biology", "Bio"));
		expected.add(new Course(3, "Chemistry", "Chem"));
		expected.add(new Course(4, "Physics", "Phy"));

		assertEquals(expected, actual);
	}

	@AfterAll
	public static void cleanUp() throws Exception {
		databaseTester.onTearDown();
		databaseTester = null;
	}

}
