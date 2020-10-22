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

import ru.stepev.dao.GroupDao;
import ru.stepev.dao.StudentDao;
import ru.stepev.data.DataHelper;
import ru.stepev.model.Group;

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
		MockitoAnnotations.initMocks(this);
		dataHelper = new DataHelper();
	}

	@Test
	public void givenGetAllGroup_whenGetAllGroup_thenGetAllGroup() {
		List<Group> expected = dataHelper.getGroups();
		when(groupDao.findAll()).thenReturn(dataHelper.getGroups());

		List<Group> actual = groupService.getAll();

		assertThat(actual).isEqualTo(expected);
		verify(groupDao, times(1)).findAll();
	}

	@Test
	public void givenAddGroup_whenAddGroup_thenAddGroup() {
		when(groupDao.findById(dataHelper.getGroupForTest().getId())).thenReturn(Optional.empty());
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(0).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(0)));
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(1).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(1)));
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(2).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(2)));

		groupService.add(dataHelper.getGroupForTest());

		verify(studentDao, times(1)).findById(dataHelper.getGroupForTest().getStudents().get(0).getId());
		verify(studentDao, times(1)).findById(dataHelper.getGroupForTest().getStudents().get(1).getId());
		verify(studentDao, times(1)).findById(dataHelper.getGroupForTest().getStudents().get(2).getId());
		verify(groupDao, times(1)).findById(dataHelper.getGroupForTest().getId());
		verify(groupDao, times(1)).create(dataHelper.getGroupForTest());
	}
	
	@Test
	public void givenAddGroupWithWrongData_whenAddGroupWithWrongData_thenNotAddGroup() {
		when(groupDao.findById(dataHelper.getGroupForTest().getId())).thenReturn(Optional.empty());
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(0).getId()))
				.thenReturn(Optional.empty());
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(1).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(1)));
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(2).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(2)));

		groupService.add(dataHelper.getGroupForTest());

		verify(studentDao, times(1)).findById(dataHelper.getGroupForTest().getStudents().get(0).getId());
		verify(studentDao, times(1)).findById(dataHelper.getGroupForTest().getStudents().get(1).getId());
		verify(studentDao, times(1)).findById(dataHelper.getGroupForTest().getStudents().get(2).getId());
		verify(groupDao, times(1)).findById(dataHelper.getGroupForTest().getId());
		verify(groupDao, times(0)).create(dataHelper.getGroupForTest());
	}

	@Test
	public void givenUpdateGroup_whenUpdateGroup_thenUpdateGroup() {
		when(groupDao.findById(dataHelper.getGroupForTest().getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest()));
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(0).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(0)));
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(1).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(1)));
		when(studentDao.findById(dataHelper.getGroupForTest().getStudents().get(2).getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest().getStudents().get(2)));

		groupService.update(dataHelper.getGroupForTest());

		verify(studentDao, times(1)).findById(dataHelper.getGroupForTest().getStudents().get(0).getId());
		verify(studentDao, times(1)).findById(dataHelper.getGroupForTest().getStudents().get(1).getId());
		verify(studentDao, times(1)).findById(dataHelper.getGroupForTest().getStudents().get(2).getId());
		verify(groupDao, times(1)).findById(dataHelper.getGroupForTest().getId());
		verify(groupDao, times(1)).update(dataHelper.getGroupForTest());
	}

	@Test
	public void givenDeleteGroup_whenDeleteGroup_thenDeleteGroup() {
		when(groupDao.findById(dataHelper.getGroupForTest().getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest()));

		groupService.delete(dataHelper.getGroupForTest());

		verify(groupDao, times(1)).findById(dataHelper.getGroupForTest().getId());
		verify(groupDao, times(1)).delete(dataHelper.getGroupForTest().getId());
	}

	@Test
	public void givenGetGroupById_whenGetGroupById_thenGetGroupById() {
		Optional<Group> expected = Optional.of(dataHelper.getGroupForTest());
		when(groupDao.findById(1)).thenReturn(Optional.of(dataHelper.getGroupForTest()));

		Optional<Group> actual = groupService.getById(1);

		assertThat(actual).isEqualTo(expected);
		verify(groupDao, times(1)).findById(1);
	}

	@Test
	public void givenGetGroupByStudentId_whenGetGroupByStudentId_thenGetGroupByStudentId() {
		Optional<Group> expected = Optional.of(dataHelper.getGroupForTest());
		when(groupDao.findByStudentId(1)).thenReturn(Optional.of(dataHelper.getGroupForTest()));

		Optional<Group> actual = groupService.findByStudentId(1);

		assertThat(actual).isEqualTo(expected);
		verify(groupDao, times(1)).findByStudentId(1);
	}
}
