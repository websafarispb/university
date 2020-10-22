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

import ru.stepev.dao.ClassroomDao;
import ru.stepev.data.DataHelper;
import ru.stepev.model.Classroom;

public class ClassroomServiceTest {

	@Mock
	private ClassroomDao classroomDao;

	@InjectMocks
	private ClassroomService classroomService;

	private DataHelper dataHelper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
		dataHelper = new DataHelper();
	}

	@Test
	public void givenFindAllClassrooms_whenFindAllClassrooms_thenFindAllClassroom() {
		List<Classroom> expected = dataHelper.getClassrooms();
		when(classroomDao.findAll()).thenReturn(dataHelper.getClassrooms());

		List<Classroom> actual = classroomService.getAll();

		assertThat(actual).isEqualTo(expected);
		verify(classroomDao).findAll();
	}

	@Test
	public void givenFindClassroomById_whenFindClassroom_thenClassroomHasFound() {
		Optional<Classroom> expected = Optional.of(dataHelper.getClassrooms().get(0));
		when(classroomDao.findById(1)).thenReturn(Optional.of(dataHelper.getClassrooms().get(0)));

		Optional<Classroom> actual = classroomService.getById(1);

		assertThat(expected).isEqualTo(actual);
		verify(classroomDao).findById(1);
	}

	@Test
	public void givenAddClassroom_whenAddClassroom_thenClassroomHasAdded() {
		when(classroomDao.findById(dataHelper.getClassroomForCreate().getId())).thenReturn(Optional.empty());

		classroomService.add(dataHelper.getClassroomForCreate());

		verify(classroomDao, times(1)).findById(dataHelper.getClassroomForCreate().getId());
		verify(classroomDao, times(1)).create(dataHelper.getClassroomForCreate());
	}

	@Test
	public void givenDeleteClassroom_whenDeleteClassroom_thenClassroomWasDeleted() {
		when(classroomDao.findById(dataHelper.getClassroomForDelete().getId())).thenReturn(Optional.of(dataHelper.getClassroomForDelete()));

		classroomService.delete(dataHelper.getClassroomForDelete());

		verify(classroomDao, times(1)).findById(dataHelper.getClassroomForDelete().getId());
		verify(classroomDao, times(1)).delete(dataHelper.getClassroomForDelete().getId());
	}

	@Test
	public void givenUpdateClassroom_whenUpdateClassroom_thenClassroomWasUpdated() {
		when(classroomDao.findById(dataHelper.getClassroomForUpdate().getId())).thenReturn(Optional.of(dataHelper.getClassroomForUpdate()));

		classroomService.update(dataHelper.getClassroomForUpdate());

		verify(classroomDao, times(1)).findById(dataHelper.getClassroomForUpdate().getId());
		verify(classroomDao, times(1)).update(dataHelper.getClassroomForUpdate());
	}

}
