package ru.stepev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.stepev.dao.CourseDao;
import ru.stepev.dao.TeacherDao;
import ru.stepev.data.DataHelper;
import ru.stepev.model.Course;

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
		MockitoAnnotations.initMocks(this);
		dataHelper = new DataHelper();
	}

	@Test
	public void givenAddCourse_whenAddCourse_thenAddCourse() {
		when(courseDao.findById(dataHelper.getCourseForTest().getId())).thenReturn(Optional.empty());
		when(teacherDao.findAll()).thenReturn(dataHelper.getTeachers());

		courseService.add(dataHelper.getCourseForTest());

		verify(teacherDao, times(1)).findAll();
		verify(courseDao, times(1)).findById(dataHelper.getCourseForTest().getId());
		verify(courseDao, times(1)).create(dataHelper.getCourseForTest());		
	}

	@Test
	public void givenUpdateCourse_whenUpdateCourse_thenUpdateCourse() {
		when(courseDao.findById(dataHelper.getCourseForTest().getId())).thenReturn(Optional.of(dataHelper.getCourseForTest()));
		when(teacherDao.findAll()).thenReturn(dataHelper.getTeachers());

		courseService.update(dataHelper.getCourseForTest());

		verify(teacherDao, times(1)).findAll();
		verify(courseDao, times(1)).findById(dataHelper.getCourseForTest().getId());
		verify(courseDao, times(1)).update(dataHelper.getCourseForTest());
	}

	@Test
	public void givenDeleteCourse_whenDeleteCourse_thenDeleteCourse() {
		when(courseDao.findById(dataHelper.getCourseForTest().getId())).thenReturn(Optional.of(dataHelper.getCourseForTest()));

		courseService.delete(dataHelper.getCourseForTest());

		verify(courseDao, times(1)).findById(dataHelper.getCourseForTest().getId());
		verify(courseDao, times(1)).delete(dataHelper.getCourseForTest().getId());
	}

	@Test
	public void givenGetCourseById_whenGetCourseById_thenGetCourseById() {
		Optional<Course> expected = Optional.of(dataHelper.getCourses().get(0));
		when(courseDao.findById(1)).thenReturn(Optional.of(dataHelper.getCourses().get(0)));

		Optional<Course> actual = courseService.getById(1);

		assertThat(actual).isEqualTo(expected);
		verify(courseDao, times(1)).findById(1);
	}

	@Test
	public void givenGetCourseByStudent_whenGetCourseByStudentthenGetCourseByStudent() {
		List<Course> expected = dataHelper.getCourses();
		when(courseDao.findByStudentId(1)).thenReturn(dataHelper.getCourses());

		List<Course> actual = courseService.getByStudent(dataHelper.getStudentForTest());

		assertThat(actual).isEqualTo(expected);
		verify(courseDao, times(1)).findByStudentId(1);
	}

	@Test
	public void givenGetCourseByTeacher_whenGetCourseByTeacher_thenGetCourseByTeacher() {
		List<Course> expected = dataHelper.getCourses();
		when(courseDao.findByTeacherId(1)).thenReturn(dataHelper.getCourses());

		List<Course> actual = courseService.getByTeacher(dataHelper.getTeacherForTest());

		assertThat(actual).isEqualTo(expected);
		verify(courseDao, times(1)).findByTeacherId(1);
	}
}
