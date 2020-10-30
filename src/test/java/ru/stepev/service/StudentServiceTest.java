package ru.stepev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.stepev.dao.StudentDao;
import ru.stepev.model.Student;

import static ru.stepev.data.DataTest.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

	@Mock
	private StudentDao studentDao;

	@InjectMocks
	private StudentService studentService;

	@Test
	public void givenStudent_whenAddStudentDoesNotExist_thenAddStudent() {
		when(studentDao.findById(studentForTest.getId())).thenReturn(Optional.empty());

		studentService.add(studentForTest);

		verify(studentDao).create(studentForTest);
	}

	@Test
	public void givenStudent_whenAddStudentExist_thenDoNotAddStudent() {
		when(studentDao.findById(studentForTest.getId())).thenReturn(Optional.of(studentForTest));

		studentService.add(studentForTest);

		verify(studentDao, never()).create(studentForTest);
	}

	@Test
	public void givenStudent_whenUpdateStudentExist_thenUpdateStudent() {
		when(studentDao.findById(studentForTest.getId())).thenReturn(Optional.of(studentForTest));

		studentService.update(studentForTest);

		verify(studentDao).update(studentForTest);
	}

	@Test
	public void givenStudent_whenUpdateStudentDoesNotExist_thenDoNotUpdateStudent() {
		when(studentDao.findById(studentForTest.getId())).thenReturn(Optional.empty());

		studentService.update(studentForTest);

		verify(studentDao, never()).update(studentForTest);
	}

	@Test
	public void givenStudent_whenDeleteStudentExist_thenDeleteStudent() {
		when(studentDao.findById(studentForTest.getId())).thenReturn(Optional.of(studentForTest));

		studentService.delete(studentForTest);

		verify(studentDao).delete(studentForTest.getId());
	}

	@Test
	public void givenStudent_whenDeleteStudentDoeNotExist_thenDoNotDeleteStudent() {
		when(studentDao.findById(studentForTest.getId())).thenReturn(Optional.empty());

		studentService.delete(studentForTest);

		verify(studentDao, never()).delete(studentForTest.getId());
	}

	@Test
	public void findAllStudents_whenGetAll_thenGetAllStudents() {
		when(studentDao.findAll()).thenReturn(expectedStudents);

		List<Student> actualStudents = studentService.getAll();

		assertThat(actualStudents).isEqualTo(expectedStudents);
	}

	@Test
	public void givenStudentId_whenGetById_thenGetStudentById() {
		Optional<Student> expected = Optional.of(studentForTest);
		when(studentDao.findById(1)).thenReturn(Optional.of(studentForTest));

		Optional<Student> actual = studentService.getById(1);

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void givenFirstAndLastNameOfStudents_whenGetByFirstAndLastNames_thenGetStudentsByFirstAndLastNames() {
		Student student = studentForTest;
		List<Student> expected = new ArrayList<>();
		expected.add(student);
		when(studentDao.findByFirstAndLastNames(student.getFirstName(), student.getLastName())).thenReturn(expected);

		List<Student> actual = studentService.getByFirstAndLastNames(student.getFirstName(), student.getLastName());

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void givenGroupId_whenGetByGroupId_thenGetStudentsByGroupId() {
		List<Student> expected = expectedStudents.subList(0, 1);
		when(studentDao.findByGroupId(1)).thenReturn(expectedStudents.subList(0, 1));

		List<Student> actual = studentService.getByGroupId(1);

		assertThat(actual).isEqualTo(expected);
	}
}
