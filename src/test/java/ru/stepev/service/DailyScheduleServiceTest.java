package ru.stepev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.stepev.dao.DailyScheduleDao;
import ru.stepev.dao.jdbc.JdbcGroupDao;
import ru.stepev.data.DataHelper;
import ru.stepev.model.DailySchedule;

@ExtendWith(MockitoExtension.class)
public class DailyScheduleServiceTest {

	@Mock
	private DailyScheduleDao dailyScheduleDao;

	@Mock
	private JdbcGroupDao groupDao;

	@InjectMocks
	private DailyScheduleService dailyScheduleService;

	private DataHelper dataHelper;

	@BeforeEach
	void setUp() {
		dataHelper = new DataHelper();
	}

	@Test
	public void givenDailySchedule_whenDailySchedulesDoeNotExist_thenAddDailySchedules() {
		when(dailyScheduleDao.findById(dataHelper.getDailyScheduleForCreate().getId())).thenReturn(Optional.empty());

		dailyScheduleService.add(dataHelper.getDailyScheduleForCreate());

		verify(dailyScheduleDao).create(dataHelper.getDailyScheduleForCreate());
	}

	@Test
	public void givenDailySchedule_whenDailySchedulesExist_thenDoNotAddDailySchedules() {
		when(dailyScheduleDao.findById(dataHelper.getDailyScheduleForCreate().getId()))
				.thenReturn(Optional.of(dataHelper.getDailyScheduleForCreate()));

		dailyScheduleService.add(dataHelper.getDailyScheduleForCreate());

		verify(dailyScheduleDao, times(0)).create(dataHelper.getDailyScheduleForCreate());
	}

	@Test
	public void givenDailySchedule_whenDailySchedulesExist_thenUpdateDailySchedules() {
		when(dailyScheduleDao.findById(dataHelper.getDailyScheduleForCreate().getId()))
				.thenReturn(Optional.of(dataHelper.getDailyScheduleForCreate()));

		dailyScheduleService.update(dataHelper.getDailyScheduleForCreate());

		verify(dailyScheduleDao).update(dataHelper.getDailyScheduleForCreate());
	}
	
	@Test
	public void givenDailySchedule_whenDailySchedulesDoesNotExist_thenDoNotUpdateDailySchedules() {
		when(dailyScheduleDao.findById(dataHelper.getDailyScheduleForCreate().getId()))
				.thenReturn(Optional.empty());

		dailyScheduleService.update(dataHelper.getDailyScheduleForCreate());

		verify(dailyScheduleDao, times(0)).update(dataHelper.getDailyScheduleForCreate());
	}


	@Test
	public void givenDailySchedule_whenDailySchedulesExist_thenDeleteDailySchedules() {
		when(dailyScheduleDao.findById(dataHelper.getDailyScheduleForCreate().getId()))
				.thenReturn(Optional.of(dataHelper.getDailyScheduleForCreate()));

		dailyScheduleService.delete(dataHelper.getDailyScheduleForCreate());

		verify(dailyScheduleDao, times(1)).delete(dataHelper.getDailyScheduleForCreate().getId());
	}
	
	@Test
	public void givenDailySchedule_whenDailySchedulesDoesNotExist_thenDoNotDeleteDailySchedules() {
		when(dailyScheduleDao.findById(dataHelper.getDailyScheduleForCreate().getId()))
				.thenReturn(Optional.empty());

		dailyScheduleService.delete(dataHelper.getDailyScheduleForCreate());

		verify(dailyScheduleDao, times(0)).delete(dataHelper.getDailyScheduleForCreate().getId());
	}

	@Test
	public void findAllDailySchedules_whenFindAllDailySchedules_thenGetAllDailySchedules() {
		List<DailySchedule> expected = dataHelper.getDailySchedules();

		when(dailyScheduleDao.findAll()).thenReturn(dataHelper.getDailySchedules());

		List<DailySchedule> actual = dailyScheduleService.getAll();

		assertThat(actual).isEqualTo(expected);
	}

	@Test
	public void givenDatePeriod_whenSchedulesByDatePeriodExist_thenGetSchedulesByDatePeriod() {
		LocalDate firstDate = LocalDate.of(2020, 9, 7);
		LocalDate lastDate = LocalDate.of(2020, 9, 11);
		List<DailySchedule> expected = dataHelper.getDailySchedules();
		when(dailyScheduleDao.findAllByDatePeriod(firstDate, lastDate)).thenReturn(expected);

		List<DailySchedule> actual = dailyScheduleService.getAllByDatePeriod(firstDate, lastDate);

		assertThat(actual).isEqualTo(expected);
		verify(dailyScheduleDao, times(1)).findAllByDatePeriod(firstDate, lastDate);
	}

	@Test
	public void givenTeacherId_whenScheduleForTeacherExist_thenGetScheduleForTeacher() {
		LocalDate firstDate = LocalDate.of(2020, 9, 7);
		LocalDate lastDate = LocalDate.of(2020, 9, 10);
		List<DailySchedule> expected = dataHelper.getDailySchedules().subList(0, 2);
		when(dailyScheduleDao.findByTeacherIdAndPeriodOfTime(dataHelper.getTeacherForTest().getId(), firstDate,
				lastDate)).thenReturn(expected);

		List<DailySchedule> actual = dailyScheduleService.getScheduleForTeacher(dataHelper.getTeacherForTest().getId(),
				firstDate, lastDate);

		assertThat(actual).isEqualTo(expected);
		verify(dailyScheduleDao, times(1)).findByTeacherIdAndPeriodOfTime(dataHelper.getTeacherForTest().getId(),
				firstDate, lastDate);
	}

	@Test
	public void givenStudentId_whenScheduleForStudentExist_thenGetScheduleForStudent() {
		LocalDate firstDate = LocalDate.of(2020, 9, 7);
		LocalDate lastDate = LocalDate.of(2020, 9, 10);
		List<DailySchedule> expected = dataHelper.getDailySchedules().subList(0, 3);
		when(dailyScheduleDao.findByGroupAndPeriodOfTime(dataHelper.getGroupForTest(), firstDate, lastDate))
				.thenReturn(expected);
		when(groupDao.findByStudentId(dataHelper.getStudentForTest().getId()))
				.thenReturn(Optional.of(dataHelper.getGroupForTest()));

		List<DailySchedule> actual = dailyScheduleService.getScheduleForStudent(dataHelper.getStudentForTest().getId(),
				firstDate, lastDate);

		assertThat(actual).isEqualTo(expected);
		verify(dailyScheduleDao, times(1)).findByGroupAndPeriodOfTime(dataHelper.getGroupForTest(), firstDate,
				lastDate);
		verify(groupDao, times(1)).findByStudentId(dataHelper.getStudentForTest().getId());
	}

	@Test
	public void givenScheduleId_whenScheduleByIdExist_thenGetScheduleById() {
		DailySchedule expected = dataHelper.getDailySchedules().get(3);
		when(dailyScheduleDao.findById(dataHelper.getDailySchedules().get(3).getId()))
				.thenReturn(Optional.of(expected));

		DailySchedule actual = dailyScheduleService.getById(dataHelper.getDailySchedules().get(3).getId()).get();

		assertThat(actual).isEqualTo(expected);
		verify(dailyScheduleDao, times(1)).findById(dataHelper.getDailySchedules().get(3).getId());
	}

	@Test
	public void givenDate_whenScheduleByDateExist_thenGetScheduleByDate() {
		LocalDate date = LocalDate.of(2020, 9, 10);
		DailySchedule expected = dataHelper.getDailySchedules().get(3);
		when(dailyScheduleDao.findByDate(date)).thenReturn(Optional.of(expected));

		DailySchedule actual = dailyScheduleService.getByDate(date).get();

		assertThat(actual).isEqualTo(expected);
		verify(dailyScheduleDao, times(1)).findByDate(date);
	}

}
