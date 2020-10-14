package ru.stepev.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import ru.stepev.dao.DailyScheduleDao;
import ru.stepev.dao.GroupDao;
import ru.stepev.model.DailySchedule;
import ru.stepev.model.Group;
import ru.stepev.model.Student;
import ru.stepev.model.Teacher;

@Component
public class DailyScheduleService {
	
	private DailyScheduleDao dailyScheduale;
	private GroupDao groupDao;
	
	public DailyScheduleService(DailyScheduleDao dailyScheduale, GroupDao groupDao) {
		this.dailyScheduale = dailyScheduale;
		this.groupDao = groupDao;
	}
	
	public void add(DailySchedule dailySchedule) {
		dailyScheduale.create(dailySchedule);
	}
	
	public void update(DailySchedule dailySchedule) {
		dailyScheduale.update(dailySchedule);
	}
	
	public void delete(int dailyScheduleId) {
		dailyScheduale.delete(dailyScheduleId);
	}
	
	public Optional<DailySchedule> getById(int scheduleId){
		return dailyScheduale.findById(scheduleId);
	}
	
	public Optional<DailySchedule> getByDate(LocalDate date){
		return dailyScheduale.findByDate(date);
	}
	
	public List<DailySchedule> getAll(){
		return dailyScheduale.findAll();
	}
	
	public List<DailySchedule> getAllByDatePeriod(LocalDate firstDate, LocalDate lastDate){
		return dailyScheduale.findAllByDatePeriod(firstDate, lastDate);
	}
	
	public List<DailySchedule> getScheduleForTeacher(int teacherId, LocalDate firstDate, LocalDate lastDate){
		return dailyScheduale.findByTeacherIdAndPeriodOfTime(teacherId, firstDate, lastDate);
	}
	
	public List<DailySchedule> getScheduleForStudent(int studentId, LocalDate firstDate, LocalDate lastDate){
		Group group = groupDao.findByStudentId(studentId).get();
		return dailyScheduale.findByGroupAndPeriodOfTime(group, firstDate, lastDate);
	}
}
