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
import org.mockito.junit.jupiter.MockitoExtension;

import ru.stepev.dao.TeacherDao;
import ru.stepev.data.DataHelper;
import ru.stepev.model.Teacher;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

	@Mock
	private TeacherDao teacherDao;

	@InjectMocks
	private TeacherService teacherService;

	private DataHelper dataHelper;

	@BeforeEach
	void setUp() {
		dataHelper = new DataHelper();
	}

	@Test
	public void givenTeacher_whenTeacherDoesNotExist_thenAddTeacher() {
		when(teacherDao.findById(dataHelper.getTeacherForTest().getId())).thenReturn(Optional.empty());

		teacherService.add(dataHelper.getTeacherForTest());

		verify(teacherDao).create(dataHelper.getTeacherForTest());
	}
	
	@Test
	public void givenTeacher_whenTeacherExist_thenDoNotAddTeacher() {
		when(teacherDao.findById(dataHelper.getTeacherForTest().getId())).thenReturn(Optional.of(dataHelper.getTeacherForTest()));

		teacherService.add(dataHelper.getTeacherForTest());

		verify(teacherDao, times(0)).create(dataHelper.getTeacherForTest());
	}


	@Test
	public void givenTeacher_whenTeacherExist_thenUpdateTeacher() {
		when(teacherDao.findById(dataHelper.getTeacherForTest().getId())).thenReturn(Optional.of(dataHelper.getTeacherForTest()));

		teacherService.update(dataHelper.getTeacherForTest());

		verify(teacherDao).update(dataHelper.getTeacherForTest());
	}
	
	@Test
	public void givenTeacher_whenTeacherDoesNotExist_thenDoNotUpdateTeacher() {
		when(teacherDao.findById(dataHelper.getTeacherForTest().getId())).thenReturn(Optional.empty());

		teacherService.update(dataHelper.getTeacherForTest());

		verify(teacherDao, times(0)).update(dataHelper.getTeacherForTest());
	}

	@Test
	public void givenTeacher_whenTeacherExist_thenDeleteTeacher() {
		when(teacherDao.findById(dataHelper.getTeacherForTest().getId())).thenReturn(Optional.of(dataHelper.getTeacherForTest()));

		teacherService.delete(dataHelper.getTeacherForTest());

		verify(teacherDao).delete(dataHelper.getTeacherForTest().getId());
	}
	
	@Test
	public void givenTeacher_whenTeacherDoeNotExist_thenDoNotDeleteTeacher() {
		when(teacherDao.findById(dataHelper.getTeacherForTest().getId())).thenReturn(Optional.empty());

		teacherService.delete(dataHelper.getTeacherForTest());

		verify(teacherDao, times(0)).delete(dataHelper.getTeacherForTest().getId());
	}

	@Test
	public void findAllTeachers_whenFindAllTeachers_thenGetAllTeachers() {
		List<Teacher> expected = dataHelper.getTeachers();
		when(teacherDao.findAll()).thenReturn(dataHelper.getTeachers());

		List<Teacher> actual = teacherService.getAll();

		assertThat(actual).isEqualTo(expected);
		verify(teacherDao, times(1)).findAll();
	}

	@Test
	public void givenTeacherId_whenTeacherExist_thenGetTeacherById() {
		Optional<Teacher> expected = Optional.of(dataHelper.getTeacherForTest());
		when(teacherDao.findById(1)).thenReturn(Optional.of(dataHelper.getTeacherForTest()));

		Optional<Teacher> actual = teacherService.getById(1);

		assertThat(actual).isEqualTo(expected);
		verify(teacherDao, times(1)).findById(1);
	}
}
