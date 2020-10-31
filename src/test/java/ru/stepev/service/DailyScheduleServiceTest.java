package ru.stepev.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.stepev.dao.DailyScheduleDao;
import ru.stepev.dao.jdbc.JdbcGroupDao;
import ru.stepev.model.DailySchedule;

import static ru.stepev.data.DataTest.*;

@ExtendWith(MockitoExtension.class)
public class DailyScheduleServiceTest {

	@Mock
	private DailyScheduleDao dailyScheduleDao;

	@Mock
	private JdbcGroupDao groupDao;

	@InjectMocks
	private DailyScheduleService dailyScheduleService;

	@Test
	public void givenDailySchedule_whenAddDailySchedulesDoeNotExist_thenAddDailySchedules() {
		when(dailyScheduleDao.findById(dailyScheduleForCreate.getId())).thenReturn(Optional.empty());

		dailyScheduleService.add(dailyScheduleForCreate);

		verify(dailyScheduleDao).create(dailyScheduleForCreate);
	}

	@Test
	public void givenDailySchedule_whenAddDailySchedulesExist_thenDoNotAddDailySchedules() {
		when(dailyScheduleDao.findById(dailyScheduleForCreate.getId()))
				.thenReturn(Optional.of(dailyScheduleForCreate));

		dailyScheduleService.add(dailyScheduleForCreate);

		verify(dailyScheduleDao, never()).create(dailyScheduleForCreate);
	}

	@Test
	public void givenDailySchedule_whenUpdateDailySchedulesExist_thenUpdateDailySchedules() {
		when(dailyScheduleDao.findById(dailyScheduleForCreate.getId()))
				.thenReturn(Optional.of(dailyScheduleForCreate));

		dailyScheduleService.update(dailyScheduleForCreate);

		verify(dailyScheduleDao).update(dailyScheduleForCreate);
	}
	
	@Test
	public void givenDailySchedule_whenUpdateDailySchedulesDoesNotExist_thenDoNotUpdateDailySchedules() {
		when(dailyScheduleDao.findById(dailyScheduleForCreate.getId()))
				.thenReturn(Optional.empty());

		dailyScheduleService.update(dailyScheduleForCreate);

		verify(dailyScheduleDao, never()).update(dailyScheduleForCreate);
	}


	@Test
	public void givenDailySchedule_whenDeleteDailySchedulesExist_thenDeleteDailySchedules() {
		when(dailyScheduleDao.findById(dailyScheduleForCreate.getId()))
				.thenReturn(Optional.of(dailyScheduleForCreate));

		dailyScheduleService.delete(dailyScheduleForCreate);

		verify(dailyScheduleDao, times(1)).delete(dailyScheduleForCreate.getId());
	}
	
	@Test
	public void givenDailySchedule_whenDeleteDailySchedulesDoesNotExist_thenDoNotDeleteDailySchedules() {
		when(dailyScheduleDao.findById(dailyScheduleForCreate.getId()))
				.thenReturn(Optional.empty());

		dailyScheduleService.delete(dailyScheduleForCreate);

		verify(dailyScheduleDao, never()).delete(dailyScheduleForCreate.getId());
	}

	@Test
	public void findAllDailySchedules_whenGetAll_thenGetAllDailySchedules() {
		when(dailyScheduleDao.findAll()).thenReturn(expectedDailySchedules);

		List<DailySchedule> actualDailySchedules = dailyScheduleService.getAll();

		assertThat(actualDailySchedules).isEqualTo(expectedDailySchedules);
	}

	@Test
	public void givenDatePeriod_whenGetAllByDatePeriodSchedulesExist_thenGetSchedules() {
		LocalDate firstDate = LocalDate.of(2020, 9, 7);
		LocalDate lastDate = LocalDate.of(2020, 9, 11);
		when(dailyScheduleDao.findAllByDatePeriod(firstDate, lastDate)).thenReturn(expectedDailySchedules);

		List<DailySchedule> actualDailySchedules = dailyScheduleService.getAllByDatePeriod(firstDate, lastDate);

		assertThat(actualDailySchedules).isEqualTo(expectedDailySchedules);
		verify(dailyScheduleDao, times(1)).findAllByDatePeriod(firstDate, lastDate);
	}

	@Test
	public void givenTeacherId_whenGetScheduleForTeacherScheduleExist_thenGetSchedule() {
		LocalDate firstDate = LocalDate.of(2020, 9, 7);
		LocalDate lastDate = LocalDate.of(2020, 9, 10);
		List<DailySchedule> expected = expectedDailySchedules.subList(0, 2);
		when(dailyScheduleDao.findByTeacherIdAndPeriodOfTime(dailyScheduleForCreate.getId(), firstDate,
				lastDate)).thenReturn(expected);

		List<DailySchedule> actual = dailyScheduleService.getScheduleForTeacher(dailyScheduleForCreate.getId(),
				firstDate, lastDate);

		assertThat(actual).isEqualTo(expected);
		verify(dailyScheduleDao, times(1)).findByTeacherIdAndPeriodOfTime(dailyScheduleForCreate.getId(),
				firstDate, lastDate);
	}

	@Test
	public void givenStudentId_whenGetScheduleForStudentScheduleExist_thenGetSchedule() {
		LocalDate firstDate = LocalDate.of(2020, 9, 7);
		LocalDate lastDate = LocalDate.of(2020, 9, 10);
		List<DailySchedule> expected = expectedDailySchedules.subList(0, 3);
		when(dailyScheduleDao.findByGroupAndPeriodOfTime(groupForTest, firstDate, lastDate))
				.thenReturn(expected);
		when(groupDao.findByStudentId(studentForTest.getId()))
				.thenReturn(Optional.of(groupForTest));

		List<DailySchedule> actual = dailyScheduleService.getScheduleForStudent(studentForTest.getId(),
				firstDate, lastDate);

		assertThat(actual).isEqualTo(expected);
		verify(dailyScheduleDao, times(1)).findByGroupAndPeriodOfTime(groupForTest, firstDate,
				lastDate);
		verify(groupDao, times(1)).findByStudentId(studentForTest.getId());
	}

	@Test
	public void givenScheduleId_whenGetByIdScheduleExist_thenGetSchedule() {
		DailySchedule expected = expectedDailySchedules.get(3);
		when(dailyScheduleDao.findById(expectedDailySchedules.get(3).getId()))
				.thenReturn(Optional.of(expected));

		DailySchedule actual = dailyScheduleService.getById(expectedDailySchedules.get(3).getId()).get();

		assertThat(actual).isEqualTo(expected);
		verify(dailyScheduleDao, times(1)).findById(expectedDailySchedules.get(3).getId());
	}

	@Test
	public void givenDate_whenGetByDateScheduleExist_thenGetSchedule() {
		LocalDate date = LocalDate.of(2020, 9, 10);
		DailySchedule expected = expectedDailySchedules.get(3);
		when(dailyScheduleDao.findByDate(date)).thenReturn(Optional.of(expected));

		DailySchedule actual = dailyScheduleService.getByDate(date).get();

		assertThat(actual).isEqualTo(expected);
		verify(dailyScheduleDao, times(1)).findByDate(date);
	}

}
