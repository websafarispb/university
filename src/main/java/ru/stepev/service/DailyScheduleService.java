package ru.stepev.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import ru.stepev.dao.DailyScheduleDao;
import ru.stepev.dao.GroupDao;
import ru.stepev.model.DailySchedule;
import ru.stepev.model.Group;

@Component
public class DailyScheduleService {

	private DailyScheduleDao dailyScheduleDao;
	private GroupDao groupDao;

	public DailyScheduleService(DailyScheduleDao dailyScheduale, GroupDao groupDao) {
		this.dailyScheduleDao = dailyScheduale;
		this.groupDao = groupDao;
	}

	public void add(DailySchedule dailySchedule) {
		if (!isDailyScheduleExist(dailySchedule)) {
			dailyScheduleDao.create(dailySchedule);
		}
	}

	public void update(DailySchedule dailySchedule) {
		if (isDailyScheduleExist(dailySchedule)) {
			dailyScheduleDao.update(dailySchedule);
		}
	}

	public void delete(DailySchedule dailySchedule) {
		if (isDailyScheduleExist(dailySchedule)) {
			dailyScheduleDao.delete(dailySchedule.getId());
		}
	}

	public Optional<DailySchedule> getById(int scheduleId) {
		return dailyScheduleDao.findById(scheduleId);
	}

	public Optional<DailySchedule> getByDate(LocalDate date) {
		return dailyScheduleDao.findByDate(date);
	}

	public List<DailySchedule> getAll() {
		return dailyScheduleDao.findAll();
	}

	public List<DailySchedule> getAllByDatePeriod(LocalDate firstDate, LocalDate lastDate) {
		return dailyScheduleDao.findAllByDatePeriod(firstDate, lastDate);
	}

	public List<DailySchedule> getScheduleForTeacher(int teacherId, LocalDate firstDate, LocalDate lastDate) {
		return dailyScheduleDao.findByTeacherIdAndPeriodOfTime(teacherId, firstDate, lastDate);
	}

	public List<DailySchedule> getScheduleForStudent(int studentId, LocalDate firstDate, LocalDate lastDate) {
		Group group = groupDao.findByStudentId(studentId).get();
		return dailyScheduleDao.findByGroupAndPeriodOfTime(group, firstDate, lastDate);
	}
	
	private boolean isDailyScheduleExist(DailySchedule dailySchedule) {
		return dailyScheduleDao.findById(dailySchedule.getId()).isPresent();
	}
}
