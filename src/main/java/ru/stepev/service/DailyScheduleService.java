package ru.stepev.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.stepev.dao.DailyScheduleDao;
import ru.stepev.dao.GroupDao;
import ru.stepev.exception.EntityAlreadyExistException;
import ru.stepev.exception.EntityNotFoundException;
import ru.stepev.model.DailySchedule;
import ru.stepev.model.Group;

@Component
@Slf4j
public class DailyScheduleService {

	private DailyScheduleDao dailyScheduleDao;
	private GroupDao groupDao;

	public DailyScheduleService(DailyScheduleDao dailyScheduale, GroupDao groupDao) {
		this.dailyScheduleDao = dailyScheduale;
		this.groupDao = groupDao;
	}

	public void add(DailySchedule dailySchedule) {
		checkDailyScheduleNotExist(dailySchedule.getId());
		dailyScheduleDao.create(dailySchedule);
		log.debug("DailySchedule with date {} was created", dailySchedule.getDate());

	}

	public void update(DailySchedule dailySchedule) {
		checkDailyScheduleExist(dailySchedule.getId());
		dailyScheduleDao.update(dailySchedule);
		log.debug("DailySchedule with date {} was updated", dailySchedule.getDate());
	}

	public void delete(DailySchedule dailySchedule) {
		checkDailyScheduleExist(dailySchedule.getId());
		dailyScheduleDao.delete(dailySchedule.getId());
		log.debug("Delete DailySchedule with date {}  was deleted", dailySchedule.getDate());
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

	public void checkDailyScheduleNotExist(int dailyScheduleId) {
		if (dailyScheduleDao.findById(dailyScheduleId).isPresent()) {
			throw new EntityAlreadyExistException(
					String.format("DailySchedule with Id %s already exist", dailyScheduleId));
		}
	}

	public void checkDailyScheduleExist(int dailyScheduleId) {
		if (dailyScheduleDao.findById(dailyScheduleId).isEmpty()) {
			throw new EntityNotFoundException(
					String.format("DailySchedule with Id %s doesn't exist", dailyScheduleId));
		}
	}
}
