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

import ru.stepev.dao.rowmapper.GroupRowMapper;
import ru.stepev.model.Group;
import ru.stepev.model.Student;

public class GroupDaoTest {

	private static IDatabaseTester databaseTester;
	private static JdbcTemplate jdbcTemplate;
	private static GroupDao groupDao;

	@BeforeAll
	public static void init() throws Exception {
		DataSource dataSourse = new EmbeddedDatabaseBuilder().generateUniqueName(true).setType(EmbeddedDatabaseType.H2)
				.setScriptEncoding("UTF-8").ignoreFailedDrops(true).addScript("schema.sql").addScript("data.sql")
				.build();
		jdbcTemplate = new JdbcTemplate(dataSourse);
		DataSourceDatabaseTester dataSourceDatabaseTester = new DataSourceDatabaseTester(dataSourse);
		databaseTester = dataSourceDatabaseTester; // (IDatabaseTester)

		GroupRowMapper groupRowMapper = new GroupRowMapper();
		groupDao = new GroupDao(jdbcTemplate, groupRowMapper);
	}

	@Test
	public void createOneGroupTest() throws Exception {

		List<Group> expected = new ArrayList<>();
		expected.add(new Group(1, "a2a2"));
		expected.add(new Group(2, "b2b2"));
		expected.add(new Group(4, "f2f2"));
		expected.add(new Group(5, "e2e2"));

		groupDao.create(new Group("e2e2"));

		IDataSet databaseDataSet = databaseTester.getConnection().createDataSet();
		ITable actualTable = databaseDataSet.getTable("GROUPS");

		List<Group> actual = new ArrayList<>();
		for (int i = 0; i < actualTable.getRowCount(); i++) {
			actual.add(new Group(Integer.parseInt(actualTable.getValue(i, "group_id").toString()),
					actualTable.getValue(i, "group_name").toString()));
		}
		assertEquals(expected, actual);
	}

	@Test
	public void updateGroupByIdTest() throws Exception {

		List<Group> expected = new ArrayList<>();
		expected.add(new Group(1, "a2a2"));
		expected.add(new Group(2, "b2b2"));
		expected.add(new Group(4, "f2f2"));

		groupDao.update(new Group(4, "f2f2"), 4);
		List<Group> actual = groupDao.findAll();

		assertEquals(expected, actual);
	}

	@Test
	public void deleteGroupByIdTest() throws Exception {

		List<Group> expected = new ArrayList<>();
		expected.add(new Group(1, "a2a2"));
		expected.add(new Group(2, "b2b2"));
		expected.add(new Group(4, "d2d2"));

		groupDao.delete(3);
		List<Group> actual = groupDao.findAll();

		assertEquals(expected, actual);
	}

	@Test
	public void findGroupByIdTest() throws Exception {

		Group expected = new Group(2, "b2b2");
		Group actual = groupDao.findById(2);

		assertEquals(expected, actual);
	}

	@Test
	public void findAllCoursesInBasaTest() throws Exception {

		List<Group> actual = groupDao.findAll();

		List<Group> expected = new ArrayList<>();
		expected.add(new Group(1, "a2a2"));
		expected.add(new Group(2, "b2b2"));
		expected.add(new Group(3, "c2c2"));
		expected.add(new Group(4, "d2d2"));

		assertEquals(expected, actual);
	}

	@Test
	public void getGroupByStudentId() {

		List<Group> expected = new ArrayList<>();
		expected.add(new Group(2, "b2b2"));
		List<Group> actual = groupDao.getGroupByStudentId(2);
		assertEquals(expected, actual);
	}

	@Test
	public void assignStudentsToGroup() throws Exception {

		List<Student> students = new ArrayList<>();
		students.add(new Student(2, 124, "Ivan", "Petrov", "2020-09-04", "webIP@mail.ru", "MALE", "City18"));
		Group group = new Group(1, "a2a2");
		List<Group> expectedGroup = new ArrayList<>();
		expectedGroup.add(group);
		expectedGroup.add(new Group(2, "b2b2"));
		groupDao.assignToStudents(group, students);

		List<Group> actualGroup = groupDao.getGroupByStudentId(2);

		assertEquals(expectedGroup, actualGroup);
	}

	@AfterAll
	public static void cleanUp() throws Exception {
		databaseTester.onTearDown();
		databaseTester = null;
	}
}
