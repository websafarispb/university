package ru.stepev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.stepev.config.TestConfig;
import ru.stepev.dao.TeacherDao;
import ru.stepev.data.DataHelper;
import ru.stepev.model.Teacher;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class TeacherServiceTest {

	@Mock
	private TeacherDao teacherDao;

	@InjectMocks
	private TeacherService teacherService;

	@Autowired
	private DataHelper dataHelper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenAddTeacher_whenAddTeacher_thenAddTeacher() {

		teacherService.add(dataHelper.getTeacherForTest());

		verify(teacherDao, times(1)).create(dataHelper.getTeacherForTest());
	}

	@Test
	public void givenUpdateTeacher_whenUpdateTeacher_thenUpdateTeacher() {

		teacherService.update(dataHelper.getTeacherForTest());

		verify(teacherDao, times(1)).update(dataHelper.getTeacherForTest());
	}

	@Test
	public void givenDeleteTeacher_whenDeleteTeacher_thenDeleteTeacher() {

		teacherService.delete(dataHelper.getTeacherForTest().getId());

		verify(teacherDao, times(1)).delete(dataHelper.getTeacherForTest().getId());
	}

	@Test
	public void givenGetAllTeachers_whenGetAllTeachers_thenGetAllTeachers() {
		List<Teacher> expected = dataHelper.getTeachers();
		when(teacherDao.findAll()).thenReturn(dataHelper.getTeachers());

		List<Teacher> actual = teacherService.getAll();

		assertThat(actual).isEqualTo(expected);
		verify(teacherDao, times(1)).findAll();
	}

	@Test
	public void givenGetTeacherById_whenGetTeacherById_thenGetTeacherById() {
		Optional<Teacher> expected = Optional.of(dataHelper.getTeacherForTest());
		when(teacherDao.findById(1)).thenReturn(Optional.of(dataHelper.getTeacherForTest()));

		Optional<Teacher> actual = teacherService.getById(1);

		assertThat(actual).isEqualTo(expected);
		verify(teacherDao, times(1)).findById(1);
	}
}
