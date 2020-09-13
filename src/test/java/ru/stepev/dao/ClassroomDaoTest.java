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

import ru.stepev.dao.rowmapper.ClassroomRowMapper;
import ru.stepev.model.Classroom;

public class ClassroomDaoTest {

	private static IDatabaseTester databaseTester;
	private static JdbcTemplate jdbcTemplate;
	private static ClassroomDao classroomDao;

	@BeforeAll
	public static void init() throws Exception {
		DataSource dataSourse = new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(EmbeddedDatabaseType.H2)
				.setScriptEncoding("UTF-8").ignoreFailedDrops(true).addScript("schema.sql").addScript("data.sql")
				.build();
		jdbcTemplate = new JdbcTemplate(dataSourse);
		DataSourceDatabaseTester dataSourceDatabaseTester = new DataSourceDatabaseTester(dataSourse);
		databaseTester = dataSourceDatabaseTester;
		ClassroomRowMapper classroomRowMapper = new ClassroomRowMapper();
		classroomDao = new ClassroomDao(jdbcTemplate, classroomRowMapper);
	}

	@Test
	public void createOneCourseTest() throws Exception {

		List<Classroom> expected = new ArrayList<>();
		expected.add(new Classroom(1, "101", 50));
		expected.add(new Classroom(2, "102", 40));
		expected.add(new Classroom(4, "104", 20));
		expected.add(new Classroom(5, "105", 10));

		classroomDao.create(new Classroom("105", 10));

		IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("CLASSROOMS");

		List<Classroom> actual = new ArrayList<>();
		for (int i = 0; i < actualTable.getRowCount(); i++) {
			actual.add(new Classroom(Integer.parseInt(actualTable.getValue(i, "classroom_id").toString()),
					actualTable.getValue(i, "classroom_address").toString(),
					Integer.parseInt(actualTable.getValue(i, "classroom_capacity").toString())));
		}
		assertEquals(expected, actual);
	}

	@Test
	public void findClassroomByIdTest() throws Exception {

		Classroom expected = new Classroom(2, "102", 40);
		Classroom actual = classroomDao.findById(2);

		assertEquals(expected, actual);
	}

	@Test
	public void updateClassroomByIdTest() throws Exception {

		List<Classroom> expected = new ArrayList<>();
		expected.add(new Classroom(1, "101", 50));
		expected.add(new Classroom(2, "102", 40));
		expected.add(new Classroom(4, "205", 200));
		expected.add(new Classroom(5, "105", 10));

		classroomDao.update(new Classroom(4, "205", 200), 4);
		List<Classroom> actual = classroomDao.findAllClassrooms();

		assertEquals(expected, actual);
	}

	@Test
	public void deleteClassroomByIdTest() throws Exception {

		List<Classroom> expected = new ArrayList<>();
		expected.add(new Classroom(1, "101", 50));
		expected.add(new Classroom(2, "102", 40));
		expected.add(new Classroom(4, "104", 20));

		classroomDao.delete(3);
		List<Classroom> actual = classroomDao.findAllClassrooms();

		assertEquals(expected, actual);
	}

	@Test
	public void findAllClassroomsInBasaTest() throws Exception {

		List<Classroom> actual = classroomDao.findAllClassrooms();

		List<Classroom> expected = new ArrayList<>();
		expected.add(new Classroom(1, "101", 50));
		expected.add(new Classroom(2, "102", 40));
		expected.add(new Classroom(3, "103", 30));
		expected.add(new Classroom(4, "104", 20));

		assertEquals(expected, actual);
	}

	@AfterAll
	public static void cleanUp() throws Exception {
		databaseTester.onTearDown();
		databaseTester = null;
	}

}
