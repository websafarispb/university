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
		if (!isDailyScheduleExist(dailySchedule)) {
			dailyScheduleDao.create(dailySchedule);
			log.debug("DailySchedule with date {} was created", dailySchedule.getDate());
		} else {
			log.warn("DailySchedule with date {} is already exist", dailySchedule.getDate());
			throw new EntityAlreadyExistException(
					String.format("Can not create DailySchedule with date %s DailySchedule already exist",
							dailySchedule.getDate().toString()));
		}
	}

	public void update(DailySchedule dailySchedule) {
		log.debug("Updating DailySchedule with date {}", dailySchedule.getDate());
		if (isDailyScheduleExist(dailySchedule)) {
			dailyScheduleDao.update(dailySchedule);
		} else {
			log.warn("DailySchedule with date {} doesn't exist", dailySchedule.getDate());
			throw new EntityNotFoundException(
					String.format("Can not update DailySchedule with date %s DailySchedule doesn't exist",
							dailySchedule.getDate().toString()));
		}
	}

	public void delete(DailySchedule dailySchedule) {
		log.debug("Delete DailySchedule with date {}", dailySchedule.getDate());
		if (isDailyScheduleExist(dailySchedule)) {
			dailyScheduleDao.delete(dailySchedule.getId());
		} else {
			log.warn("Classroom with address {} doesn't exist", dailySchedule.getDate());
			throw new EntityNotFoundException(
					String.format("Can not delete DailySchedule with date %s DailySchedule doesn't exist",
							dailySchedule.getDate().toString()));
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
		log.debug("Is DailySchedule with date {} exist?", dailySchedule.getDate());
		return dailyScheduleDao.findById(dailySchedule.getId()).isPresent();
	}
}
