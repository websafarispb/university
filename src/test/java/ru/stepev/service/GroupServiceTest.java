package ru.stepev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.stepev.dao.GroupDao;
import ru.stepev.dao.StudentDao;
import ru.stepev.exception.EntityAlreadyExistException;
import ru.stepev.exception.EntityNotFoundException;
import ru.stepev.exception.StudentdsNotFoundException;
import ru.stepev.model.Group;

import static ru.stepev.data.DataTest.*;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

	@Mock
	private GroupDao groupDao;
	@Mock
	private StudentDao studentDao;

	@InjectMocks
	private GroupService groupService;

	@Test
	public void findAllGroup_whenGetAll_thenGetAllGroup() {
		when(groupDao.findAll()).thenReturn(expectedGroups);

		List<Group> actualGroups = groupService.getAll();

		assertThat(actualGroups).isEqualTo(expectedGroups);
	}

	@Test
	public void givenGroup_whenAddGroupDoesNotExist_thenAddGroup() {
		when(groupDao.findById(groupForTest.getId())).thenReturn(Optional.empty());
		when(studentDao.findById(groupForTest.getStudents().get(0).getId()))
				.thenReturn(Optional.of(groupForTest.getStudents().get(0)));
		when(studentDao.findById(groupForTest.getStudents().get(1).getId()))
				.thenReturn(Optional.of(groupForTest.getStudents().get(1)));
		when(studentDao.findById(groupForTest.getStudents().get(2).getId()))
				.thenReturn(Optional.of(groupForTest.getStudents().get(2)));

		groupService.add(groupForTest);

		verify(groupDao).create(groupForTest);
	}

	@Test
	public void givenGroup_whenAddGroupExist_thenDoNotAddGroup() {
		when(groupDao.findById(groupForTest.getId())).thenReturn(Optional.of(groupForTest));

		EntityAlreadyExistException exception = assertThrows(EntityAlreadyExistException.class,
				() -> groupService.add(groupForTest));

		assertThat(exception.getMessage()).isEqualTo("Group with name %s already exist", groupForTest.getName());
		verify(groupDao, never()).create(groupForTest);
	}

	@Test
	public void givenGroupWithWrongData_whenAddDataWrong_thenNotAddGroup() {
		when(groupDao.findById(groupForTest.getId())).thenReturn(Optional.empty());
		when(studentDao.findById(groupForTest.getStudents().get(0).getId())).thenReturn(Optional.empty());
		when(studentDao.findById(groupForTest.getStudents().get(1).getId()))
				.thenReturn(Optional.of(groupForTest.getStudents().get(1)));
		when(studentDao.findById(groupForTest.getStudents().get(2).getId()))
				.thenReturn(Optional.of(groupForTest.getStudents().get(2)));

		StudentdsNotFoundException exception = assertThrows(StudentdsNotFoundException.class,
				() -> groupService.add(groupForTest));

		assertThat(exception.getMessage()).isEqualTo("Students don't exist", groupForTest.getName());
		verify(groupDao, never()).create(groupForTest);
	}

	@Test
	public void givenGroup_whenUpdateGroupExist_thenUpdateGroup() {
		when(groupDao.findById(groupForTest.getId())).thenReturn(Optional.of(groupForTest));
		when(studentDao.findById(groupForTest.getStudents().get(0).getId()))
				.thenReturn(Optional.of(groupForTest.getStudents().get(0)));
		when(studentDao.findById(groupForTest.getStudents().get(1).getId()))
				.thenReturn(Optional.of(groupForTest.getStudents().get(1)));
		when(studentDao.findById(groupForTest.getStudents().get(2).getId()))
				.thenReturn(Optional.of(groupForTest.getStudents().get(2)));

		groupService.update(groupForTest);

		verify(groupDao).update(groupForTest);
	}

	@Test
	public void givenGroup_whenUpdateGroupDoesNotExist_thenDoNotUpdateGroup() {
		when(groupDao.findById(groupForTest.getId())).thenReturn(Optional.empty());

		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
				() -> groupService.update(groupForTest));

		assertThat(exception.getMessage()).isEqualTo("Group with name %s doesn't exist", groupForTest.getName());
		verify(groupDao, never()).update(groupForTest);
	}

	@Test
	public void givenGroup_whenDeleteGroupExist_thenDeleteGroup() {
		when(groupDao.findById(groupForTest.getId())).thenReturn(Optional.of(groupForTest));

		groupService.delete(groupForTest);

		verify(groupDao).delete(groupForTest.getId());
	}

	@Test
	public void givenGroup_whenDeleteGroupDoesNotExist_thenDoNotDeleteGroup() {
		when(groupDao.findById(groupForTest.getId())).thenReturn(Optional.empty());

		EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
				() -> groupService.delete(groupForTest));

		assertThat(exception.getMessage()).isEqualTo("Group with name %s doesn't exist", groupForTest.getName());
		verify(groupDao, never()).delete(groupForTest.getId());
	}

	@Test
	public void givenGroupId_whenGetById_thenGetGroup() {
		Optional<Group> expected = Optional.of(groupForTest);
		when(groupDao.findById(1)).thenReturn(Optional.of(groupForTest));

		Optional<Group> actual = groupService.getById(1);

		assertThat(actual).isEqualTo(expected);
		verify(groupDao, times(1)).findById(1);
	}

	@Test
	public void givenStudentId_whenFindByStudentId_thenGetGroup() {
		Optional<Group> expected = Optional.of(groupForTest);
		when(groupDao.findByStudentId(1)).thenReturn(Optional.of(groupForTest));

		Optional<Group> actual = groupService.findByStudentId(1);

		assertThat(actual).isEqualTo(expected);
		verify(groupDao, times(1)).findByStudentId(1);
	}
}
