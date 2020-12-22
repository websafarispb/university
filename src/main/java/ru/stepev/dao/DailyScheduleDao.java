package ru.stepev.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ru.stepev.model.DailySchedule;
import ru.stepev.model.Group;
import ru.stepev.utils.Paginator;

public interface DailyScheduleDao {
	
	public void create(DailySchedule dailySchedule);
	public void update(DailySchedule dailySchedule);
	public void delete(int dailyScheduleId);
	public Optional<DailySchedule> findById(int scheduleId);
	public Optional<DailySchedule> findByDate(LocalDate date);
	public List<DailySchedule> findAll();
	public List<DailySchedule> findAllByDatePeriod(LocalDate firstDate, LocalDate lastDate);
	public List<DailySchedule> findByTeacherIdAndPeriodOfTime(int teacherId, LocalDate firstDate, LocalDate lastDate);
	public List<DailySchedule> findByGroupAndPeriodOfTime(Group group, LocalDate firstDate, LocalDate lastDate);
	public int findNumberOfItems();
	public List<DailySchedule> findAndSortByDate(int numberOfItems, int offset);
	public List<DailySchedule> getAndSortById(int numberOfItems, int offset);
	public List<DailySchedule> findAndSortedByTeacherIdAndPeriodOfTime(int teacherId, LocalDate firstDay,
			LocalDate lastDay, Paginator paginator);
	public List<DailySchedule> findAndSortedByGroupAndPeriodOfTime(Group group, LocalDate firstDay, LocalDate lastDay, Paginator paginator);
}
