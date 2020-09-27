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
	public void create_whenCreateOneClassroom_thenTableClassroomsMustHaveCorrectCountOfRows() throws Exception {
		int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "CLASSROOMS") + 1;
		Classroom expectedClassroom = new Classroom(5, "105", 10);

		Classroom actualClassroom = new Classroom("105", 10);
		classroomDao.create(actualClassroom);
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "CLASSROOMS");

		assertEquals(expectedRows, actualRows);
		assertEquals(expectedClassroom, actualClassroom);
	}

	@Test
	public void findClassroomById_shouldFindCorrectClassroomById_thenTableHaveOne() throws Exception {
		Classroom expectedClassroom = new Classroom(2, "102", 40);
		Classroom actualClassroom = classroomDao.findById(2);

		assertEquals(expectedClassroom, actualClassroom);
	}

	@Test
	public void updateClassroomById_whenUpdateClassroomById_thenTableMustHaveCorrectRow() throws Exception {
		Classroom updatedClassroom = new Classroom(4, "205", 200);
		int expectedRows = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "CLASSROOMS",
				String.format("id = %d AND classroom_address = '%s' AND classroom_capacity = %d",
						updatedClassroom.getId(), updatedClassroom.getAddress(), updatedClassroom.getCapacity())) + 1;
		classroomDao.update(updatedClassroom);

		int actualRows = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "CLASSROOMS",
				String.format("id = %d AND classroom_address = '%s' AND classroom_capacity = %d",
						updatedClassroom.getId(), updatedClassroom.getAddress(), updatedClassroom.getCapacity()));

		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void deleteClassroomById_whenDeleteClassroomById_thenCountOfRowsInTableMustDecrease() throws Exception {
		int expectedRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "CLASSROOMS") - 1;
		classroomDao.delete(3);
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "CLASSROOMS");
		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void findAllClassrooms_shouldFindAllClassrooms_whenTableHaveMoreThenOne() throws Exception {
		List<Classroom> expectedClassrooms = new ArrayList<>();
		expectedClassrooms.add(new Classroom(1, "101", 50));
		expectedClassrooms.add(new Classroom(2, "102", 40));
		expectedClassrooms.add(new Classroom(4, "104", 20));
		expectedClassrooms.add(new Classroom(5, "105", 10));

		List<Classroom> actualClassrooms = classroomDao.findAll();
		assertEquals(expectedClassrooms, actualClassrooms);
	}
}
