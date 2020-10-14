package ru.stepev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
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
import ru.stepev.dao.StudentDao;
import ru.stepev.data.DataHelper;
import ru.stepev.model.Student;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class StudentServiceTest {
	
	@Mock
	private StudentDao studentDao;

	@InjectMocks
	private StudentService studentService;

	@Autowired
	private DataHelper dataHelper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenAddStudent_whenAddStudent_thenAddStudent() {

		studentService.add(dataHelper.getStudentForTest());
		
		verify(studentDao, times(1)).create(dataHelper.getStudentForTest());
	}
	
	@Test
	public void givenUpdateStudent_whenUpdateStudent_thenUpdateStudent() {

		studentService.update(dataHelper.getStudentForTest());
		
		verify(studentDao, times(1)).update(dataHelper.getStudentForTest());
	}
	
	@Test
	public void givenDeleteStudent_whenDeleteStudent_thenDeleteStudent() {

		studentService.delete(dataHelper.getStudentForTest().getId());
		
		verify(studentDao, times(1)).delete(dataHelper.getStudentForTest().getId());
	}
	
	@Test
	public void givenGetAllStudents_whenGetAllStudents_thenGetAllStudents() {

		List<Student> expected = dataHelper.getStudents();
		when(studentDao.findAll()).thenReturn(dataHelper.getStudents());

		List<Student> actual = studentService.getAll();

		assertThat(actual).isEqualTo(expected);
		verify(studentDao, times(1)).findAll();
	}
	
	@Test
	public void givenGetStudentById_whenGetStudentById_thenGetStudentById() {

		Optional<Student> expected = Optional.of(dataHelper.getStudentForTest());
		when(studentDao.findById(1)).thenReturn(Optional.of(dataHelper.getStudentForTest()));

		Optional<Student> actual = studentService.getById(1);

		assertThat(actual).isEqualTo(expected);
		verify(studentDao, times(1)).findById(1);
	}
	
	@Test
	public void givenGetStudentsByFirstAndLastNames_whenGetStudentsByFirstAndLastNames_thenGetStudentsByFirstAndLastNames() {
		Student student = dataHelper.getStudentForTest();
		List<Student> expected = new ArrayList<>();
		expected.add(student);
		when(studentDao.findByFirstAndLastNames(student.getFirstName(), student.getLastName())).thenReturn(expected);

		List<Student> actual = studentService.getByFirstAndLastNames(student.getFirstName(), student.getLastName());

		assertThat(actual).isEqualTo(expected);
		verify(studentDao, times(1)).findByFirstAndLastNames(student.getFirstName(), student.getLastName());
	}

	@Test
	public void givenGetStudentsByGroupId_whenGetStudentsByGroupId_thenGetStudentsByGroupId() {

		List<Student> expected = dataHelper.getStudents().subList(0, 1);
		when(studentDao.findByGroupId(1)).thenReturn(dataHelper.getStudents().subList(0, 1));

		List<Student> actual = studentService.getByGroupId(1);

		assertThat(actual).isEqualTo(expected);
		verify(studentDao, times(1)).findByGroupId(1);
	}
}
