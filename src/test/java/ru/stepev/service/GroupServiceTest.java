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
import ru.stepev.dao.GroupDao;
import ru.stepev.data.DataHelper;
import ru.stepev.model.Group;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
public class GroupServiceTest {

	@Mock
	private GroupDao groupDao;

	@InjectMocks
	private GroupService groupService;

	@Autowired
	private DataHelper dataHelper;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
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

		groupService.add(dataHelper.getGroupForTest());

		verify(groupDao, times(1)).create(dataHelper.getGroupForTest());
	}

	@Test
	public void givenUpdateGroup_whenUpdateGroup_thenUpdateGroup() {

		groupService.update(dataHelper.getGroupForTest());

		verify(groupDao, times(1)).update(dataHelper.getGroupForTest());
	}

	@Test
	public void givenDeleteGroup_whenDeleteGroup_thenDeleteGroup() {

		groupService.delete(dataHelper.getGroupForTest().getId());

		verify(groupDao, times(1)).delete(dataHelper.getGroupForTest().getId());
	}
	
	@Test
	public void givenGetGroupById_whenGetGroupById_thenGetGroupById() {
		Optional <Group> expected =  Optional.of(dataHelper.getGroupForTest());
		when(groupDao.findById(1)).thenReturn(Optional.of(dataHelper.getGroupForTest()));

		Optional <Group> actual = groupService.getById(1);

		assertThat(actual).isEqualTo(expected);
		verify(groupDao, times(1)).findById(1);
	}
	
	@Test
	public void givenGetGroupByStudentId_whenGetGroupByStudentId_thenGetGroupByStudentId() {
		Optional <Group> expected =  Optional.of(dataHelper.getGroupForTest());
		when(groupDao.findByStudentId(1)).thenReturn(Optional.of(dataHelper.getGroupForTest()));

		Optional <Group> actual = groupService.findByStudentId(1);

		assertThat(actual).isEqualTo(expected);
		verify(groupDao, times(1)).findByStudentId(1);
	}
}
