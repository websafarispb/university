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
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import ru.stepev.config.TestConfig;
import ru.stepev.dao.ClassroomDao;
import ru.stepev.data.DataHelper;
import ru.stepev.model.Classroom;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class ClassroomServiceTest {

	@Mock
	private ClassroomDao classroomDao;

	@InjectMocks
	private ClassroomService classroomService;

	@Autowired
	private DataHelper dataHelper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void givenFindAllClassrooms_whenFindAllClassrooms_thenFindAllClassroom() {
		List<Classroom> expected = dataHelper.getClassrooms();
		when(classroomDao.findAll()).thenReturn(dataHelper.getClassrooms());

		List<Classroom> actual = classroomService.findAll();

		assertThat(actual).isEqualTo(expected);
		verify(classroomDao).findAll();
	}

	@Test
	public void givenFindClassroomById_whenFindClassroom_thenClassroomHasFound() {
		Optional<Classroom> expected = Optional.of(dataHelper.getClassrooms().get(0));
		when(classroomDao.findById(1)).thenReturn(Optional.of(dataHelper.getClassrooms().get(0)));

		Optional<Classroom> actual = classroomService.findById(1);

		assertThat(expected).isEqualTo(actual);
		verify(classroomDao).findById(1);
	}

	@Test
	public void givenAddClassroom_whenAddClassroom_thenClassroomHasAdded() {

		classroomService.add(dataHelper.getClassroomForCreate());

		verify(classroomDao, times(1)).create(dataHelper.getClassroomForCreate());
	}

	@Test
	public void givenDeleteClassroom_whenDeleteClassroom_thenClassroomWasDeleted() {

		classroomService.delete(dataHelper.getClassroomForDelete().getId());

		verify(classroomDao, times(1)).delete(dataHelper.getClassroomForDelete().getId());
	}

	@Test
	public void givenUpdateClassroom_whenUpdateClassroom_thenClassroomWasUpdated() {

		classroomService.update(dataHelper.getClassroomForUpdate());

		verify(classroomDao, times(1)).update(dataHelper.getClassroomForUpdate());
	}

}
