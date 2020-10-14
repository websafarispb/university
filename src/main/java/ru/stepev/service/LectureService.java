package ru.stepev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import ru.stepev.dao.LectureDao;
import ru.stepev.model.Lecture;

@Component
public class LectureService {
	
	private LectureDao lectureDao;
	
	public LectureService(LectureDao lectureDao) {
		this.lectureDao = lectureDao;
	}

	public void add(Lecture lecture) {
		lectureDao.create(lecture);
	}

	public void update(Lecture lecture) {
		lectureDao.update(lecture);
	}

	public void delete(int lectureId) {
		lectureDao.delete(lectureId);
	}

	public Optional<Lecture> getById(int lectureId){
		return lectureDao.findById(lectureId);
	}

	public List<Lecture> getByDailyScheduleId(int dailyScheduleId){
		return lectureDao.findByDailyScheduleId(dailyScheduleId);
	}

	public List<Lecture> getAll(){
		return lectureDao.findAll();
	}
}
