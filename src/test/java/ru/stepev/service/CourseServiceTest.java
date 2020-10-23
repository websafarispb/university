package ru.stepev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.stepev.dao.CourseDao;
import ru.stepev.dao.TeacherDao;
import ru.stepev.data.DataHelper;
import ru.stepev.model.Course;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

	@Mock
	private CourseDao courseDao;
	@Mock
	private TeacherDao teacherDao;

	@InjectMocks
	private CourseService courseService;

	private DataHelper dataHelper;

	@BeforeEach
	void setUp() {
		dataHelper = new DataHelper();
	}

	@Test
	public void givenCourse_whenCourseDoesNotExist_thenAddCourse() {
		when(courseDao.findById(dataHelper.getCourseForTest().getId())).thenReturn(Optional.empty());
		when(teacherDao.findAll()).thenReturn(dataHelper.getTeachers());

		courseService.add(dataHelper.getCourseForTest());

		verify(courseDao, times(1)).findById(dataHelper.getCourseForTest().getId());
		verify(courseDao, times(1)).create(dataHelper.getCourseForTest());		
	}

	@Test
	public void givenCourse_whenCourseExist_thenUpdateCourse() {
		when(courseDao.findById(dataHelper.getCourseForTest().getId())).thenReturn(Optional.of(dataHelper.getCourseForTest()));
		when(teacherDao.findAll()).thenReturn(dataHelper.getTeachers());

		courseService.update(dataHelper.getCourseForTest());

		verify(courseDao, times(1)).findById(dataHelper.getCourseForTest().getId());
		verify(courseDao, times(1)).update(dataHelper.getCourseForTest());
	}

	@Test
	public void givenCourse_whenCourseExist_thenDeleteCourse() {
		when(courseDao.findById(dataHelper.getCourseForTest().getId())).thenReturn(Optional.of(dataHelper.getCourseForTest()));

		courseService.delete(dataHelper.getCourseForTest());

		verify(courseDao, times(1)).findById(dataHelper.getCourseForTest().getId());
		verify(courseDao, times(1)).delete(dataHelper.getCourseForTest().getId());
	}

	@Test
	public void givenCourseId_whenFindCourseById_thenGetCourseById() {
		Optional<Course> expected = Optional.of(dataHelper.getCourses().get(0));
		when(courseDao.findById(1)).thenReturn(Optional.of(dataHelper.getCourses().get(0)));

		Optional<Course> actual = courseService.getById(1);

		assertThat(actual).isEqualTo(expected);
		verify(courseDao, times(1)).findById(1);
	}

	@Test
	public void givenStudent_whenFindCourseByStudent_thenGetCourseByStudent() {
		List<Course> expected = dataHelper.getCourses();
		when(courseDao.findByStudentId(1)).thenReturn(dataHelper.getCourses());

		List<Course> actual = courseService.getByStudent(dataHelper.getStudentForTest());

		assertThat(actual).isEqualTo(expected);
		verify(courseDao, times(1)).findByStudentId(1);
	}

	@Test
	public void givenTeacher_whenFindCourseByTeacher_thenGetCourseByTeacher() {
		List<Course> expected = dataHelper.getCourses();
		when(courseDao.findByTeacherId(1)).thenReturn(dataHelper.getCourses());

		List<Course> actual = courseService.getByTeacher(dataHelper.getTeacherForTest());

		assertThat(actual).isEqualTo(expected);
		verify(courseDao, times(1)).findByTeacherId(1);
	}
}
