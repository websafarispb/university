package ru.stepev.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
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
import ru.stepev.dao.CourseDao;
import ru.stepev.model.Course;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class CourseDaoTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private CourseDao courseDao;

	@Test
	public void givenCreateCourse_whenCreateCourse_thenCourseWillBeCreated() {
		int expectedRows = countRowsInTable(jdbcTemplate, "COURSES") + 1;
		Course expectedCourse = new Course(5, "Geography", "Geo");
		Course actualCourse = new Course("Geography", "Geo");

		courseDao.create(actualCourse);

		int actualRows = countRowsInTable(jdbcTemplate, "COURSES");
		assertEquals(expectedCourse, actualCourse);
		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void givenUpdateCourse_whenUpdateCourseById_thenCourseWillBeUpdated() {
		Course updatedCourse = new Course(4, "History", "History description");
		int expectedRows = countRowsInTableWhere(jdbcTemplate, "COURSES",
				String.format("id = %d AND course_name = '%s' AND course_description = '%s'", updatedCourse.getId(),
						updatedCourse.getName(), updatedCourse.getDescription())) + 1;

		courseDao.update(updatedCourse, 4);

		int actualRows = countRowsInTableWhere(jdbcTemplate, "COURSES",
				String.format("id = %d AND course_name = '%s' AND course_description = '%s'", updatedCourse.getId(),
						updatedCourse.getName(), updatedCourse.getDescription()));
		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void givenDelete_whenDeleteCourseById_thenCourseWillBeDeleted() {
		int expectedRows = countRowsInTable(jdbcTemplate, "COURSES") - 1;
		List<Course> expected = new ArrayList<>();
		expected.add(new Course(1, "Mathematics", "Math"));
		expected.add(new Course(2, "Biology", "Bio"));
		expected.add(new Course(4, "History", "History description"));
		expected.add(new Course(5, "Geography", "Geo"));

		courseDao.delete(3);

		List<Course> actual = courseDao.findAll();
		int actualRows = countRowsInTable(jdbcTemplate, "COURSES");
		assertEquals(expected, actual);
		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void givenFindCourseById_whenFindCourseById_thenCourseWillBeFound() {
		Course expected = new Course(2, "Biology", "Bio");

		Optional<Course> actual = courseDao.findById(2);

		assertThat(actual).get().isEqualTo(expected);
	}

	@Test
	public void givenFindCourseByIdNotExist_whenNotFindCourseByIdNotExist_thenGetEmptyOptional() {
		Optional<Course> actual = courseDao.findById(200);
		
		assertThat(actual).isEmpty();
	}

	@Test
	public void findAllCourses_shouldFindAllCourses_whenTableHaveMoreThenOne() {
		List<Course> actual = courseDao.findAll();

		int expectedRows = actual.size();
		int actualRows = countRowsInTable(jdbcTemplate, "COURSES");
		List<Course> expected = new ArrayList<>();
		expected.add(new Course(1, "Mathematics", "Math"));
		expected.add(new Course(2, "Biology", "Bio"));
		expected.add(new Course(3, "Chemistry", "Chem"));
		expected.add(new Course(4, "Physics", "Phy"));
		expected.add(new Course(5, "Geography", "Geo"));
		assertEquals(expectedRows, actualRows);
		assertThat(expected).isEqualTo(actual);
	}
}
