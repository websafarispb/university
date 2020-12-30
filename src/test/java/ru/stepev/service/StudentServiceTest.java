package ru.stepev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
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

import ru.stepev.dao.CourseDao;
import ru.stepev.dao.StudentDao;
import ru.stepev.exception.EntityAlreadyExistException;
import ru.stepev.exception.EntityNotFoundException;
import ru.stepev.model.Lecture;
import ru.stepev.model.Student;
import ru.stepev.utils.Paginator;

import static ru.stepev.data.DataTest.*;

@ExtendWith(MockitoExtension.class)
public class StudentServiceTest {

	@Mock
	private StudentDao studentDao;

	@Mock
	private CourseDao courseDao;

	@InjectMocks
	private StudentService studentService;

	@Test
	public void givenStudent_whenAddStudentDoesNotExist_thenAddStudent() {
		when(studentDao.findById(studentForTest.getId())).thenReturn(Optional.empty());
		when(courseDao.findAll()).thenReturn(expectedCourses);

		studentService.add(studentForTest);

		verify(studentDao).create(studentForTest);
	}

	@Test
	public void givenStudent_whenAddStudentExist_thenDoNotAddStudent() {
		when(studentDao.findById(studentForTest.getId())).thenReturn(Optional.of(studentForTest));

		EntityAlreadyExistException exception = assertThrows(EntityAlreadyExistException.class,
				() -> studentService.add(studentForTest));

		assertThat(exception.getMessage()).isEqualTo("Student with ID %s already exist",
				studentForTest.getId());
		verify(studentDao, never()).create(studentForTest);
	}

	@Test
	public void givenStudent_whenUpdateStudentExist_thenUpdateStudent() {
		when(studentDao.findById(studentForTest.getId())).thenReturn(Optional.of(studentForTest));
		when(courseDao.findAll()).thenReturn(expectedCourses);

		studentService.update(studentForTest);

		verify(studentDao).update(studentForTest);
	}

	@Test
	public void givenStudentWithNotExistCourse_whenUpdateStudentExist_thenNotUpdateStudent() {
		when(studentDao.findById(smartStudent.getId())).thenReturn(Optional.of(smartStudent));
		when(courseDao.findAll()).thenReturn(expectedCourses);

		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
				() -> studentService.update(smartStudent));

		assertThat(exception.getMessage()).isEqualTo("Course with name Anatomy doesn't exist");
		verify(studentDao, never()).update(smartStudent);
	}

	@Test
	public void givenStudent_whenUpdateStudentDoesNotExist_thenDoNotUpdateStudent() {
		when(studentDao.findById(studentForTest.getId())).thenReturn(Optional.empty());

		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
				() -> studentService.update(studentForTest));

		assertThat(exception.getMessage()).isEqualTo("Student with ID %s doesn't exist",
				studentForTest.getId());
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

		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
				() -> studentService.delete(studentForTest));

		assertThat(exception.getMessage()).isEqualTo("Student with ID %s doesn't exist",
				studentForTest.getId());
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
	
	@Test
	public void countNumberOfStudents_whenCountNumberOfStudents_thenGetCorrectNumberOfStudents() {
		int expected = 2;
		when(studentDao.findNumberOfItems()).thenReturn(expected);
		
		int actual = studentService.count();
		
		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void givenDiapasonOfEntities_whenGetAndSortByFirstName_thenGetSortedListByFirstName() {
		when(studentDao.findAndSortByFirstName(5, 4)).thenReturn(expectedStudents);

		List<Student> actualStudents = studentService.getAndSortByFirstName(5, 4);

		assertThat(actualStudents).isEqualTo(expectedStudents);
	}

	@Test
	public void givenDiapasonOfEntities_whenGetAndSortByLastName_thenGetSortedListByLastName() {
		when(studentDao.findAndSortByLastName(5, 4)).thenReturn(expectedStudents);

		List<Student> actualStudents = studentService.getAndSortByLastName(5, 4);

		assertThat(actualStudents).isEqualTo(expectedStudents);
	}

	@Test
	public void givenDiapasonOfEntities_whenGetAndSortById_thenGetSortedListByID() {
		when(studentDao.findAndSortById(5, 4)).thenReturn(expectedStudents);

		List<Student> actualStudents = studentService.getAndSortById(5, 4);

		assertThat(actualStudents).isEqualTo(expectedStudents);
	}

	@Test
	public void givenDiapasonOfEntities_whenGetAndSortByEmail_thenGetSortedListByEmail() {
		when(studentDao.findAndSortByEmail(5, 4)).thenReturn(expectedStudents);

		List<Student> actualStudents = studentService.getAndSortByEmail(5, 4);

		assertThat(actualStudents).isEqualTo(expectedStudents);
	}

	@Test
	public void givenDiapasonOfEntities_whenGetAndSortByAddress_thenGetSortedListByAddress() {
		when(studentDao.findAndSortByAddress(5, 4)).thenReturn(expectedStudents);

		List<Student> actualStudents = studentService.getAndSortByAddress(5, 4);

		assertThat(actualStudents).isEqualTo(expectedStudents);
	}

	@Test
	public void givenDiapasonOfEntities_whenGetAndSortById_thenGetSortedListById() {
		when(studentDao.findAndSortByBirthday(5, 4)).thenReturn(expectedStudents);

		List<Student> actualStudents = studentService.getAndSortByBirthday(5, 4);

		assertThat(actualStudents).isEqualTo(expectedStudents);
	}

	@Test
	public void givenTypeOfSorting_whenGetAndSort_thenGetSortedListOfStudentsBySortingType() {
		Paginator paginator = new Paginator(1, 1, "Last_name", 5);
		when(studentDao.findAndSortByLastName(5, 0)).thenReturn(expectedStudents);

		List<Student> actualStudents = studentService.getAndSort(paginator);

		assertThat(actualStudents).isEqualTo(expectedStudents);
	}
}
