package ru.stepev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.stepev.dao.CourseDao;
import ru.stepev.dao.TeacherDao;
import ru.stepev.exception.EntityAlreadyExistException;
import ru.stepev.exception.EntityNotFoundException;
import ru.stepev.model.Classroom;
import ru.stepev.model.Course;

import static ru.stepev.data.DataTest.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

	@Mock
	private CourseDao courseDao;
	@Mock
	private TeacherDao teacherDao;

	@InjectMocks
	private CourseService courseService;

	@Test
	public void givenCourse_whenAddCourseDoesNotExist_thenAddCourse() {
		when(courseDao.findByName(courseForCreate.getName())).thenReturn(Optional.empty());

		courseService.add(courseForCreate);

		verify(courseDao).create(courseForCreate);
	}

	@Test
	public void givenCourse_whenAddCourseExist_thenDoNotAddCourse() {
		when(courseDao.findByName(courseForCreate.getName())).thenReturn(Optional.of(courseForTest));

		EntityAlreadyExistException exception = assertThrows(EntityAlreadyExistException.class,
				() -> courseService.add(courseForCreate));

		assertThat(exception.getMessage()).isEqualTo("Course with name %s already exist", courseForCreate.getName());
		verify(courseDao, never()).create(courseForCreate);
	}

	@Test
	public void givenCourse_whenUpdateCourseExist_thenUpdateCourse() {
		when(courseDao.findById(courseForTest.getId())).thenReturn(Optional.of(courseForTest));
		when(courseDao.findByName(courseForTest.getName())).thenReturn(Optional.of(courseForTest));

		courseService.update(courseForTest);

		verify(courseDao).update(courseForTest);
	}

	@Test
	public void givenCourse_whenUpdateCourseDoesNotExist_thenDoNotUpdateCourse() {
		when(courseDao.findById(courseForTest.getId())).thenReturn(Optional.empty());

		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
				() -> courseService.update(courseForTest));

		assertThat(exception.getMessage()).isEqualTo("Course with name %s doesn't exist", courseForTest.getName());
		verify(courseDao, never()).update(courseForTest);
	}

	@Test
	public void givenCourse_whenDeleteCourseExist_thenDeleteCourse() {
		when(courseDao.findById(courseForTest.getId())).thenReturn(Optional.of(courseForTest));

		courseService.delete(courseForTest);

		verify(courseDao).delete(courseForTest.getId());
	}

	@Test
	public void givenCourse_whenDeleteCourseDoesNotExist_thenDoNotDeleteCourse() {
		when(courseDao.findById(courseForTest.getId())).thenReturn(Optional.empty());

		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
				() -> courseService.delete(courseForTest));

		assertThat(exception.getMessage()).isEqualTo("Course with name %s doesn't exist", courseForTest.getName());
		verify(courseDao, never()).delete(courseForTest.getId());
	}

	@Test
	public void givenCourseId_whenGetById_thenGetCourse() {
		Optional<Course> expected = Optional.of(expectedCourses.get(0));
		when(courseDao.findById(1)).thenReturn(Optional.of(expectedCourses.get(0)));

		Optional<Course> actual = courseService.getById(1);

		assertThat(actual).isEqualTo(expected);
		verify(courseDao).findById(1);
	}

	@Test
	public void givenStudent_whenGetByStudent_thenGetCourse() {
		when(courseDao.findByStudentId(1)).thenReturn(expectedCourses);

		List<Course> actualCourses = courseService.getByStudent(studentForTest);

		assertThat(actualCourses).isEqualTo(expectedCourses);
	}

	@Test
	public void givenTeacher_whenGetByTeacher_thenGetCourse() {
		when(courseDao.findByTeacherId(1)).thenReturn(expectedCourses);

		List<Course> actualCourses = courseService.getByTeacher(teacherForTest);

		assertThat(actualCourses).isEqualTo(expectedCourses);
	}

	@Test
	public void givenDiapasonOfEntities_whenGetAndSortByName_thenGetSortedListByName() {
		when(courseDao.findAndSortByName(5, 4)).thenReturn(expectedCourses);

		List<Course> actualCourses = courseService.getAndSortByName(5, 4);

		assertThat(actualCourses).isEqualTo(expectedCourse);
	}

	@Test
	public void givenDiapasonOfEntities_whenGetAndSortById_thenGetSortedListById() {
		when(courseDao.findAndSortById(5, 4)).thenReturn(expectedCourses);

		List<Course> actualCourses = courseService.getAndSortById(5, 4);

		assertThat(actualCourses).isEqualTo(expectedCourses);
	}

	@Test
	public void countNumberOfCourses_whenCountNumberOfCourses_thenGetCorrectNumberOfCourses() {
		int expected = 2;
		when(courseDao.findNumberOfItems()).thenReturn(expected);

		int actual = courseService.count();

		assertThat(actual).isEqualTo(expected);
	}
}
