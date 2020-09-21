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
	public void createOneCourseTest() throws Exception {

		int expectedRows = 5;
		Course expectedCourse = new Course(5, "Geography", "Geo");
		Course actualCourse = new Course("Geography", "Geo");

		courseDao.create(actualCourse);
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "COURSES");

		assertEquals(expectedCourse, actualCourse);
		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void updateCourseByIdTest() throws Exception {
		int expectedRows = 1;

		Course updatedCourse = new Course(4, "History", "History description");
		courseDao.update(updatedCourse,4);

		int actualRows = JdbcTestUtils.countRowsInTableWhere(jdbcTemplate, "COURSES",
				String.format("id = %d AND course_name = '%s' AND course_description = '%s'",
						updatedCourse.getId(), updatedCourse.getName(), updatedCourse.getDescription()));

		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void deleteCourseByIdTest() throws Exception {

		int expectedRows = 4;
		List<Course> expected = new ArrayList<>();
		expected.add(new Course(1, "Mathematics", "Math"));
		expected.add(new Course(2, "Biology", "Bio"));
		expected.add(new Course(4, "History", "History description"));
		expected.add(new Course(5, "Geography", "Geo"));

		courseDao.delete(3);
		List<Course> actual = courseDao.findAllCourses();
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "COURSES");

		assertEquals(expected, actual);
		assertEquals(expectedRows, actualRows);
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
		int actualRows = JdbcTestUtils.countRowsInTable(jdbcTemplate, "COURSES");

		int expectedRows = 4;
		List<Course> expected = new ArrayList<>();
		expected.add(new Course(1, "Mathematics", "Math"));
		expected.add(new Course(2, "Biology", "Bio"));
		expected.add(new Course(3, "Chemistry", "Chem"));
		expected.add(new Course(4, "Physics", "Phy"));

		assertEquals(expectedRows, actualRows);
		assertEquals(expected, actual);
	}

}
