package ru.stepev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.stepev.dao.TeacherDao;
import ru.stepev.exception.EntityAlreadyExistException;
import ru.stepev.exception.EntityNotFoundException;
import ru.stepev.model.Teacher;

import static ru.stepev.data.DataTest.*;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

	@Mock
	private TeacherDao teacherDao;

	@InjectMocks
	private TeacherService teacherService;

	@Test
	public void givenTeacher_whenTeacherDoesNotExist_thenAddTeacher() {
		when(teacherDao.findById(teacherForTest.getId())).thenReturn(Optional.empty());

		teacherService.add(teacherForTest);

		verify(teacherDao).create(teacherForTest);
	}

	@Test
	public void givenTeacher_whenTeacherExist_thenDoNotAddTeacher() {
		when(teacherDao.findById(teacherForTest.getId())).thenReturn(Optional.of(teacherForTest));

		EntityAlreadyExistException exception = assertThrows(EntityAlreadyExistException.class,
				() -> teacherService.add(teacherForTest));

		assertThat(exception.getMessage()).isEqualTo("Teacher with ID %s already exist",
				teacherForTest.getId());
		verify(teacherDao, never()).create(teacherForTest);
	}

	@Test
	public void givenTeacher_whenTeacherExist_thenUpdateTeacher() {
		when(teacherDao.findById(teacherForTest.getId())).thenReturn(Optional.of(teacherForTest));

		teacherService.update(teacherForTest);

		verify(teacherDao).update(teacherForTest);
	}

	@Test
	public void givenTeacher_whenTeacherDoesNotExist_thenDoNotUpdateTeacher() {
		when(teacherDao.findById(teacherForTest.getId())).thenReturn(Optional.empty());

		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
				() -> teacherService.update(teacherForTest));

		assertThat(exception.getMessage()).isEqualTo("Teacher with ID %s doesn't exist",
				teacherForTest.getId());
		verify(teacherDao, never()).update(teacherForTest);
	}

	@Test
	public void givenTeacher_whenTeacherExist_thenDeleteTeacher() {
		when(teacherDao.findById(teacherForTest.getId())).thenReturn(Optional.of(teacherForTest));

		teacherService.delete(teacherForTest);

		verify(teacherDao).delete(teacherForTest.getId());
	}

	@Test
	public void givenTeacher_whenTeacherDoeNotExist_thenDoNotDeleteTeacher() {
		when(teacherDao.findById(teacherForTest.getId())).thenReturn(Optional.empty());

		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
				() -> teacherService.delete(teacherForTest));

		assertThat(exception.getMessage()).isEqualTo("Teacher with ID %s doesn't exist",
				teacherForTest.getId());
		verify(teacherDao, never()).delete(teacherForTest.getId());
	}

	@Test
	public void findAllTeachers_whenFindAllTeachers_thenGetAllTeachers() {
		when(teacherDao.findAll()).thenReturn(expectedTeachers);

		List<Teacher> actualTeachers = teacherService.getAll();

		assertThat(actualTeachers).isEqualTo(expectedTeachers);
	}

	@Test
	public void givenTeacherId_whenTeacherExist_thenGetTeacherById() {
		Optional<Teacher> expected = Optional.of(teacherForTest);
		when(teacherDao.findById(1)).thenReturn(Optional.of(teacherForTest));

		Optional<Teacher> actual = teacherService.getById(1);

		assertThat(actual).isEqualTo(expected);
	}
}
