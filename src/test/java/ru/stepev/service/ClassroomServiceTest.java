package ru.stepev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.stepev.dao.ClassroomDao;
import ru.stepev.exception.EntityAlreadyExistException;
import ru.stepev.exception.EntityNotFoundException;
import ru.stepev.model.Classroom;

import static ru.stepev.data.DataTest.*;

@ExtendWith(MockitoExtension.class)
public class ClassroomServiceTest {

	@Mock
	private ClassroomDao classroomDao;

	@InjectMocks
	private ClassroomService classroomService;

	@Test
	public void findAllClassrooms_whenGetAllClassrooms_thenGetAllClassroom() {
		when(classroomDao.findAll()).thenReturn(expectedClassrooms);

		List<Classroom> actualClassrooms = classroomService.getAll();

		assertThat(actualClassrooms).isEqualTo(expectedClassrooms);
	}
	
	@Test
	public void findAllClassrooms_whenGetAllClassrooms_thenGetAllClassroom2() {
		List<Classroom> expectedClassrooms2 = new ArrayList<>();
		expectedClassrooms2.add(new Classroom(3, "103", 30));
		expectedClassrooms2.add(new Classroom(7, "203", 30));
		expectedClassrooms2.add(new Classroom(11, "303", 30));
		expectedClassrooms2.add(new Classroom(15, "403", 30));
		expectedClassrooms2.add(new Classroom(19, "703", 30));
		when(classroomDao.findAndSortByCapacity(5, 4)).thenReturn(expectedClassrooms2);
		
		List<Classroom> actualClassrooms = classroomService.getAndSortByCapacity(5, 4);
		
		assertThat(actualClassrooms).isEqualTo(expectedClassrooms2);
	}

	@Test
	public void givenClassroomId_whenGetByIdClassroom_thenGetClassroom() {
		Optional<Classroom> expected = Optional.of(expectedClassrooms.get(0));
		when(classroomDao.findById(1)).thenReturn(Optional.of(expectedClassrooms.get(0)));

		Optional<Classroom> actual = classroomService.getById(1);

		assertThat(expected).isEqualTo(actual);
	}

	@Test
	public void givenClassroom_whenAddClassroomExist_thenNotAddClassroom() {
		when(classroomDao.findByAddress(classroomForCreate.getAddress())).thenReturn(Optional.of(classroomForCreate));

		EntityAlreadyExistException exception = assertThrows(EntityAlreadyExistException.class,
				() -> classroomService.add(classroomForTest));

		assertThat(exception.getMessage()).isEqualTo("Classroom with address %s already exist",
				classroomForCreate.getAddress());

		verify(classroomDao, never()).create(classroomForCreate);
	}

	@Test
	public void givenClassroom_whenAddClassroomDoesNotExist_thenAddClassroom() {
		when(classroomDao.findByAddress(classroomForCreate.getAddress())).thenReturn(Optional.empty());

		classroomService.add(classroomForCreate);

		verify(classroomDao).create(classroomForCreate);
	}

	@Test
	public void givenClassroom_whenDeleteClassroomExist_thenDeleteClassroom() {
		when(classroomDao.findById(classroomForDelete.getId())).thenReturn(Optional.of(classroomForDelete));
		classroomService.delete(classroomForDelete);

		verify(classroomDao).delete(classroomForDelete.getId());
	}

	@Test
	public void givenClassroom_whenDeleteClassroomDoesNotExist_thenNotDeleteClassroom() {
		when(classroomDao.findById(classroomForDelete.getId())).thenReturn(Optional.empty());

		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
				() -> classroomService.delete(classroomForDelete));

		assertThat(exception.getMessage()).isEqualTo("Classroom with address %s doesn't exist",
				classroomForDelete.getAddress());
		verify(classroomDao, never()).delete(classroomForDelete.getId());
	}

	@Test
	public void givenClassroom_whenUpdateClassroomExist_thenUpdateClassroom() {
		when(classroomDao.findById(classroomForDelete.getId())).thenReturn(Optional.of(classroomForDelete));
		when(classroomDao.findByAddress(classroomForDelete.getAddress())).thenReturn(Optional.of(classroomForDelete));

		classroomService.update(classroomForDelete);

		verify(classroomDao).update(classroomForDelete);
	}

	@Test
	public void givenClassroom_whenUpdateClassroomDoesNotExist_thenNotUpdateClassroom() {
		when(classroomDao.findById(classroomForUpdate.getId())).thenReturn(Optional.empty());
		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
				() -> classroomService.update(classroomForUpdate));

		assertThat(exception.getMessage()).isEqualTo("Classroom with address %s doesn't exist",
				classroomForUpdate.getAddress());

		verify(classroomDao, never()).update(classroomForUpdate);
	}
}
