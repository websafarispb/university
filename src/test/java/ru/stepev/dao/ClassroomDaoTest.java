package ru.stepev.dao;

import static org.junit.Assert.assertEquals;

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
import ru.stepev.model.Classroom;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class ClassroomDaoTest {

	@Autowired
	private ClassroomDao classroomDao;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void createOneClassroomTest() throws Exception {
		int expectedRows = 5;
		Classroom expectedClassroom = new Classroom(5, "105", 10);

		Classroom actualClassroom = new Classroom("105", 10);
		classroomDao.create(actualClassroom);
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "CLASSROOMS");

		assertEquals(expectedRows, actualRows);
		assertEquals(expectedClassroom, actualClassroom);
	}

	@Test
	public void findClassroomByIdTest() throws Exception {
		Classroom expectedClassroom = new Classroom(2, "102", 40);
		Classroom actualClassroom = classroomDao.findById(2);

		assertEquals(expectedClassroom, actualClassroom);
	}

	@Test
	public void updateClassroomByIdTest() throws Exception {
		int expectedRows = 1;

		Classroom updatedClassroom = new Classroom(4, "205", 200);
		classroomDao.update(updatedClassroom);

		int actualRows = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "CLASSROOMS",
				String.format("id = %d AND classroom_address = '%s' AND classroom_capacity = %d",
						updatedClassroom.getId(), updatedClassroom.getAddress(), updatedClassroom.getCapacity()));

		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void deleteClassroomByIdTest() throws Exception {
		int expectedRows = 4;
		classroomDao.delete(3);
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "CLASSROOMS");
		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void findAllClassroomsInBasaTest() throws Exception {
		int expectedRows = 5;
		List<Classroom> expectedClassrooms = new ArrayList<>();
		expectedClassrooms.add(new Classroom(1, "101", 50));
		expectedClassrooms.add(new Classroom(2, "102", 40));
		expectedClassrooms.add(new Classroom(3, "103", 30));
		expectedClassrooms.add(new Classroom(4, "104", 20));
		expectedClassrooms.add(new Classroom(5, "105", 10));

		List<Classroom> actualClassrooms = classroomDao.findAllClassrooms();
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "CLASSROOMS");

		assertEquals(expectedClassrooms, actualClassrooms);
		assertEquals(expectedRows, actualRows);
	}
}
