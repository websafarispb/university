package ru.stepev.dao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.jdbc.JdbcTestUtils.*;
import static ru.stepev.data.DataTest.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import ru.stepev.config.TestConfig;
import ru.stepev.model.Course;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@WebAppConfiguration
public class JdbcCourseDaoTest {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	@Autowired
	private CourseDao courseDao;

	@Test
	public void givenCreateCourse_whenCreateCourse_thenCourseWillBeCreated() {
		int expectedRows = countRowsInTable(jdbcTemplate, "COURSES") + 1;
		Course expectedCourse = new Course(17, "Geography", "Geo");
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
						updatedCourse.getName(), updatedCourse.getDescription()))
				+ 1;

		courseDao.update(updatedCourse);

		int actualRows = countRowsInTableWhere(jdbcTemplate, "COURSES",
				String.format("id = %d AND course_name = '%s' AND course_description = '%s'", updatedCourse.getId(),
						updatedCourse.getName(), updatedCourse.getDescription()));
		assertEquals(expectedRows, actualRows);
	}

	@Test
	public void givenDelete_whenDeleteCourseById_thenCourseWillBeDeleted() {
		int expectedRows = countRowsInTable(jdbcTemplate, "COURSES") - 1;

		courseDao.delete(3);

		List<Course> actualCourses = courseDao.findAll();
		int actualRows = countRowsInTable(jdbcTemplate, "COURSES");	
		assertThat(actualCourses).isEqualTo(expectedCoursesAfterDeleteOne);
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
		
		List<Course> actualCourses = courseDao.findAll();

		int expectedRows = actualCourses.size();
		int actualRows = countRowsInTable(jdbcTemplate, "COURSES");
		assertEquals(expectedRows, actualRows);
		assertThat(expectedCourses).isEqualTo(actualCourses);
	}
	
	@Test
	public void findAndSortByName_whenfindAndSortByName_thenGetCorrectSortedByNameListOfCourses() {
		
		List<Course> actualCourses = courseDao.findAndSortByName(5, 0);
	
		assertThat(expectedSortedByNameCourses).isEqualTo(actualCourses);
	}
	@Test
	public void findAndSortById_whenfindAndSortById_thenGetCorrectSortedByIdListOfCourses() {
		
		List<Course> actualCourses = courseDao.findAndSortById(5, 0);
		
		assertThat(expectedSortedByIdCourses).isEqualTo(actualCourses);
	}
	
	@Test
	public void findNumberOfItems_whenFindNumberOfItem_thenGetCorrectNumberOfItems() {
		int expectedNumber = 16;
		
		int actualNumber  = courseDao.findNumberOfItems();
		
		assertThat(expectedNumber).isEqualTo(actualNumber);
	}
}
