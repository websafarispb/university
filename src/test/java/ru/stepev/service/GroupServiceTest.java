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

import ru.stepev.dao.GroupDao;
import ru.stepev.dao.StudentDao;
import ru.stepev.data.DataHelper;
import ru.stepev.model.Group;

@ExtendWith(MockitoExtension.class)
public class GroupServiceTest {

	@Mock
	private GroupDao groupDao;
	@Mock
	private StudentDao studentDao;

	@InjectMocks
	private GroupService groupService;

	private DataHelper dataHelper;

	@BeforeEach
	void setUp() {
		dataHelper = new DataHelper();
	}

	@Test
	public void findAllGroup_whenFindAllGroup_thenGetAllGroup() {
		List<Group> expected = dataHelper.getGroups();
		when(groupDao.findAll()).thenReturn(dataHelper.getGroups());

		List<Group> actual = groupService.getAll();

		assertThat(actual).isEqualTo(expected);
		verify(groupDao, times(1)).findAll();
	}

	@Test
	public void givenGroup_whenGroupDoesNotExist_thenAddGroup() {
		when(groupDao.findById(dataHelper.getGroupForTest().getId())).thenReturn(Optional.empty());
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(0).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(0)));
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(1).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(1)));
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(2).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(2)));

		groupService.add(dataHelper.getGroupForTest());

		verify(groupDao).create(dataHelper.getGroupForTest());
	}
	
	@Test
	public void givenGroup_whenGroupExist_thenDoNotAddGroup() {
		when(groupDao.findById(dataHelper.getGroupForTest().getId())).thenReturn(Optional.empty());
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(0).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(0)));
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(1).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(1)));
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(2).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(2)));

		groupService.add(dataHelper.getGroupForTest());

		verify(groupDao, times(0)).create(dataHelper.getGroupForTest());
	}
	
	@Test
	public void givenGroupWithWrongData_whenDataWrong_thenNotAddGroup() {
		when(groupDao.findById(dataHelper.getGroupForTest().getId())).thenReturn(Optional.empty());
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(0).getId()))
				.thenReturn(Optional.empty());
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(1).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(1)));
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(2).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(2)));

		groupService.add(dataHelper.getGroupForTest());

		verify(groupDao, times(0)).create(dataHelper.getGroupForTest());
	}

	@Test
	public void givenGroup_whenGroupExist_thenUpdateGroup() {
		when(groupDao.findById(dataHelper.getGroupForTest().getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest()));
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(0).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(0)));
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(1).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(1)));
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(2).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(2)));

		groupService.update(dataHelper.getGroupForTest());

		verify(groupDao).update(dataHelper.getGroupForTest());
	}
	
	@Test
	public void givenGroup_whenGroupDoesNotExist_thenDoNotUpdateGroup() {
		when(groupDao.findById(dataHelper.getGroupForTest().getId()))
				.thenReturn(Optional.empty());

		groupService.update(dataHelper.getGroupForTest());

		verify(groupDao, times(0)).update(dataHelper.getGroupForTest());
	}

	@Test
	public void givenGroup_whenGroupExist_thenDeleteGroup() {
		when(groupDao.findById(dataHelper.getGroupForTest().getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest()));

		groupService.delete(dataHelper.getGroupForTest());

		verify(groupDao).delete(dataHelper.getGroupForTest().getId());
	}
	
	@Test
	public void givenGroup_whenGroupDoesNotExist_thenDoNotDeleteGroup() {
		when(groupDao.findById(dataHelper.getGroupForTest().getId()))
				.thenReturn(Optional.empty());

		groupService.delete(dataHelper.getGroupForTest());

		verify(groupDao, times(0)).delete(dataHelper.getGroupForTest().getId());
	}

	@Test
	public void givenGroupId_whenGroupExist_thenGetGroupById() {
		Optional<Group> expected = Optional.of(dataHelper.getGroupForTest());
		when(groupDao.findById(1)).thenReturn(Optional.of(dataHelper.getGroupForTest()));

		Optional<Group> actual = groupService.getById(1);

		assertThat(actual).isEqualTo(expected);
		verify(groupDao, times(1)).findById(1);
	}

	@Test
	public void givenStudentId_whenGetGroupExist_thenGetGroupByStudentId() {
		Optional<Group> expected = Optional.of(dataHelper.getGroupForTest());
		when(groupDao.findByStudentId(1)).thenReturn(Optional.of(dataHelper.getGroupForTest()));

		Optional<Group> actual = groupService.findByStudentId(1);

		assertThat(actual).isEqualTo(expected);
		verify(groupDao, times(1)).findByStudentId(1);
	}
}
