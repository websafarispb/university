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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import ru.stepev.dao.DailyScheduleDao;
import ru.stepev.dao.jdbc.JdbcGroupDao;
import ru.stepev.data.DataHelper;
import ru.stepev.model.DailySchedule;

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
		MockitoAnnotations.initMocks(this);
		dataHelper = new DataHelper();
	}

	@Test
	public void givenAddDailySchedule_whenAddDailySchedules_thenAddDailySchedules() {
		when(dailyScheduleDao.findById(dataHelper.getDailyScheduleForCreate().getId())).thenReturn(Optional.empty());

		dailyScheduleService.add(dataHelper.getDailyScheduleForCreate());

		verify(dailyScheduleDao, times(1)).findById(dataHelper.getDailyScheduleForCreate().getId());
		verify(dailyScheduleDao, times(1)).create(dataHelper.getDailyScheduleForCreate());
	}

	@Test
	public void givenUpdateDailySchedule_whenUpdateDailySchedules_thenUpdateDailySchedules() {
		when(dailyScheduleDao.findById(dataHelper.getDailyScheduleForCreate().getId())).thenReturn(Optional.of(dataHelper.getDailyScheduleForCreate()));

		dailyScheduleService.update(dataHelper.getDailyScheduleForCreate());

		verify(dailyScheduleDao, times(1)).findById(dataHelper.getDailyScheduleForCreate().getId());
		verify(dailyScheduleDao, times(1)).update(dataHelper.getDailyScheduleForCreate());
	}

	@Test
	public void givenDeleteDailySchedule_whenDeleteDailySchedules_thenDeleteDailySchedules() {
		when(dailyScheduleDao.findById(dataHelper.getDailyScheduleForCreate().getId())).thenReturn(Optional.of(dataHelper.getDailyScheduleForCreate()));

		dailyScheduleService.delete(dataHelper.getDailyScheduleForCreate());

		verify(dailyScheduleDao, times(1)).findById(dataHelper.getDailyScheduleForCreate().getId());
		verify(dailyScheduleDao, times(1)).delete(dataHelper.getDailyScheduleForCreate().getId());
	}

	@Test
	public void givenFindAllDailySchedules_whenFindAllDailySchedules_thenFindAllDailySchedules() {
		List<DailySchedule> expected = dataHelper.getDailySchedules();

		when(dailyScheduleDao.findAll()).thenReturn(dataHelper.getDailySchedules());

		List<DailySchedule> actual = dailyScheduleService.getAll();

		assertThat(actual).isEqualTo(expected);
		verify(dailyScheduleDao, times(1)).findAll();
	}

	@Test
	public void givenGetSchedulesByDatePeriod_whenGetSchedulesByDatePeriod_thenGetSchedulesByDatePeriod() {
		LocalDate firstDate = LocalDate.of(2020, 9, 7);
		LocalDate lastDate = LocalDate.of(2020, 9, 11);
		List<DailySchedule> expected = dataHelper.getDailySchedules();
		when(dailyScheduleDao.findAllByDatePeriod(firstDate, lastDate)).thenReturn(expected);

		List<DailySchedule> actual = dailyScheduleService.getAllByDatePeriod(firstDate, lastDate);

		assertThat(actual).isEqualTo(expected);
		verify(dailyScheduleDao, times(1)).findAllByDatePeriod(firstDate, lastDate);
	}

	@Test
	public void givenGetScheduleForTeacher_whenGetScheduleForTeacher_thenGetScheduleForTeacher() {
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
	public void givenGetScheduleForStudent_whenGetScheduleForStudent_thenGetScheduleForStudent() {
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
	public void givenGetScheduleById_whenGetScheduleById_thenGetScheduleById() {
		DailySchedule expected = dataHelper.getDailySchedules().get(3);
		when(dailyScheduleDao.findById(dataHelper.getDailySchedules().get(3).getId()))
				.thenReturn(Optional.of(expected));

		DailySchedule actual = dailyScheduleService.getById(dataHelper.getDailySchedules().get(3).getId()).get();

		assertThat(actual).isEqualTo(expected);
		verify(dailyScheduleDao, times(1)).findById(dataHelper.getDailySchedules().get(3).getId());
	}

	@Test
	public void givenGetScheduleByDate_whenGetScheduleByDate_thenGetScheduleByDate() {
		LocalDate date = LocalDate.of(2020, 9, 10);
		DailySchedule expected = dataHelper.getDailySchedules().get(3);
		when(dailyScheduleDao.findByDate(date)).thenReturn(Optional.of(expected));

		DailySchedule actual = dailyScheduleService.getByDate(date).get();

		assertThat(actual).isEqualTo(expected);
		verify(dailyScheduleDao, times(1)).findByDate(date);
	}

}
