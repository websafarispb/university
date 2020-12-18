package ru.stepev.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.jdbc.JdbcTestUtils.*;

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
import ru.stepev.model.Classroom;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class JdbcClassroomDaoTest {

	@Autowired
	private ClassroomDao classroomDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void givenCreateNewClassroom_whenCreate_thenCreated() {
		int expectedRows = countRowsInTable(jdbcTemplate, "CLASSROOMS") + 1;
		Classroom expectedClassroom = new Classroom(5, "105", 10);
		Classroom actualClassroom = new Classroom("105", 10);

		classroomDao.create(actualClassroom);

		int actualRows = countRowsInTable(jdbcTemplate, "CLASSROOMS");
		assertThat(expectedRows).isEqualTo(actualRows);
		assertThat(expectedClassroom).isEqualTo(actualClassroom);
	}

	@Test
	public void givenUpdateExistedClassroomById_whenUpdateClassroomById_thenClassroomWillBeUpdated() {
		Classroom updatedClassroom = new Classroom(4, "205", 200);
		int expectedRows = countRowsInTableWhere(jdbcTemplate, "CLASSROOMS",
				String.format("id = %d AND classroom_address = '%s' AND classroom_capacity = %d",
						updatedClassroom.getId(), updatedClassroom.getAddress(), updatedClassroom.getCapacity()))
				+ 1;

		classroomDao.update(updatedClassroom);

		int actualRows = countRowsInTableWhere(jdbcTemplate, "CLASSROOMS",
				String.format("id = %d AND classroom_address = '%s' AND classroom_capacity = %d",
						updatedClassroom.getId(), updatedClassroom.getAddress(), updatedClassroom.getCapacity()));
		assertThat(expectedRows).isEqualTo(actualRows);
	}

	@Test
	public void givenFindAllClassrooms_whenFindAllClassrooms_thenFindAllClassroom() {
		List<Classroom> expectedClassrooms = new ArrayList<>();
		expectedClassrooms.add(new Classroom(1, "101", 50));
		expectedClassrooms.add(new Classroom(2, "102", 40));
		expectedClassrooms.add(new Classroom(4, "104", 20));
		expectedClassrooms.add(new Classroom(5, "105", 10));

		List<Classroom> actualClassrooms = classroomDao.findAll();

		assertThat(expectedClassrooms).isEqualTo(actualClassrooms);
	}
	
	@Test
	public void givenFindAndSortByCapacity_whenFindAndSortByCapacity_thenFindAllClassroom() {
		List<Classroom> expectedClassrooms = new ArrayList<>();
		expectedClassrooms.add(new Classroom(3, "103", 30));
		expectedClassrooms.add(new Classroom(7, "203", 30));
		expectedClassrooms.add(new Classroom(11, "303", 30));
		expectedClassrooms.add(new Classroom(15, "403", 30));
		expectedClassrooms.add(new Classroom(19, "703", 30));

		List<Classroom> actualClassrooms = classroomDao.findAndSortByCapacity(5, 4);

		assertThat(expectedClassrooms).isEqualTo(actualClassrooms);
	}

	@Test
	public void givenDeleteClassroomById_whenDeleteClassroomById_thenTableWillNotHaveGivenClassroom() {
		int expectedRows = countRowsInTable(jdbcTemplate, "CLASSROOMS") - 1;

		classroomDao.delete(3);

		int actualRows = countRowsInTable(jdbcTemplate, "CLASSROOMS");
		assertThat(expectedRows).isEqualTo(actualRows);
	}

	@Test
	public void givenFindClassroomById_whenFindClassroomById_thenFindClassroomById() {
		Classroom expectedClassroom = new Classroom(2, "102", 40);
		Optional<Classroom> actualClassroom = classroomDao.findById(2);

		assertThat(actualClassroom).get().isEqualTo(expectedClassroom);

	}

	@Test
	public void givenFindClassroomByIdNotExist_whenDidNotFindClassroomById_thenGetEmptyOptional() {
		Optional<Classroom> actualClassroom = classroomDao.findById(200);

		assertThat(actualClassroom).isEmpty();
	}
}
