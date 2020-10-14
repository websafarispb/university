package ru.stepev.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import ru.stepev.model.DailySchedule;
import ru.stepev.model.Group;

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
}
