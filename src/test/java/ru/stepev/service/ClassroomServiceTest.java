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

import ru.stepev.dao.ClassroomDao;
import ru.stepev.data.DataHelper;
import ru.stepev.model.Classroom;

@ExtendWith(MockitoExtension.class)
public class ClassroomServiceTest {

	@Mock
	private ClassroomDao classroomDao;

	@InjectMocks
	private ClassroomService classroomService;

	private DataHelper dataHelper;

	@BeforeEach
	void setUp() {
		dataHelper = new DataHelper();
	}

	@Test
	public void findAllClassrooms_whenFindAllClassrooms_thenGetAllClassroom() {
		List<Classroom> expected = dataHelper.getClassrooms();
		when(classroomDao.findAll()).thenReturn(dataHelper.getClassrooms());

		List<Classroom> actual = classroomService.getAll();

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void givenClassroomId_whenFindClassroom_thenGetClassroom() {
		Optional<Classroom> expected = Optional.of(dataHelper.getClassrooms().get(0));
		when(classroomDao.findById(1)).thenReturn(Optional.of(dataHelper.getClassrooms().get(0)));

		Optional<Classroom> actual = classroomService.getById(1);

		assertThat(expected).isEqualTo(actual);
	}
	
	@Test
	public void givenClassroom_whenClassroomExist_thenNotAddClassroom() {
		when(classroomDao.findById(dataHelper.getClassroomForCreate().getId())).thenReturn(Optional.of(dataHelper.getClassroomForCreate()));

		classroomService.add(dataHelper.getClassroomForCreate());

		verify(classroomDao, times(0)).create(dataHelper.getClassroomForCreate());
	}

	@Test
	public void givenClassroom_whenClassroomDoesNotExist_thenAddClassroom() {
		when(classroomDao.findById(dataHelper.getClassroomForCreate().getId())).thenReturn(Optional.empty());

		classroomService.add(dataHelper.getClassroomForCreate());

		verify(classroomDao).create(dataHelper.getClassroomForCreate());
	}

	@Test
	public void givenClassroom_whenClassroomExist_thenDeleteClassroom() {
		when(classroomDao.findById(dataHelper.getClassroomForDelete().getId())).thenReturn(Optional.of(dataHelper.getClassroomForDelete()));

		classroomService.delete(dataHelper.getClassroomForDelete());

		verify(classroomDao).delete(dataHelper.getClassroomForDelete().getId());
	}
	
	@Test
	public void givenClassroom_whenClassroomDoesNotExist_thenNotDeleteClassroom() {
		when(classroomDao.findById(dataHelper.getClassroomForDelete().getId())).thenReturn(Optional.empty());

		classroomService.delete(dataHelper.getClassroomForDelete());

		verify(classroomDao, times(0)).delete(dataHelper.getClassroomForDelete().getId());
	}

	@Test
	public void givenClassroom_whenClassroomExist_thenUpdateClassroom() {
		when(classroomDao.findById(dataHelper.getClassroomForUpdate().getId())).thenReturn(Optional.of(dataHelper.getClassroomForUpdate()));

		classroomService.update(dataHelper.getClassroomForUpdate());

		verify(classroomDao).update(dataHelper.getClassroomForUpdate());
	}
	
	@Test
	public void givenClassroom_whenClassroomDoesNotExist_thenNotUpdateClassroom() {
		when(classroomDao.findById(dataHelper.getClassroomForUpdate().getId())).thenReturn(Optional.empty());

		classroomService.update(dataHelper.getClassroomForUpdate());

		verify(classroomDao, times(0)).update(dataHelper.getClassroomForUpdate());
	}

	@Test
	public void givenClassroomId_whenClassroomExist_thenReturnTrue() {
		boolean expected = true;
		when(classroomDao.findById(dataHelper.getClassroomForUpdate().getId())).thenReturn(Optional.of(dataHelper.getClassroomForUpdate()));

		boolean actual = classroomService.isClassroomExist(dataHelper.getClassroomForUpdate().getId());

		assertThat(actual).isEqualTo(expected);
	}
	
	@Test
	public void givenClassroomId_whenClassroomDoesNotExist_thenReturnFalse() {
		boolean expected = false;
		when(classroomDao.findById(dataHelper.getClassroomForUpdate().getId())).thenReturn(Optional.empty());

		boolean actual = classroomService.isClassroomExist(dataHelper.getClassroomForUpdate().getId());

		assertThat(actual).isEqualTo(expected);
	}
}
