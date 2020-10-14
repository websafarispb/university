package ru.stepev.dao;

import java.util.List;
import java.util.Optional;

import ru.stepev.model.Lecture;

public interface LectureDao {

	public void create(Lecture lecture);

	public void update(Lecture lecture);

	public void delete(int lectureId);

	public Optional<Lecture> findById(int lectureId);

	public List<Lecture> findByDailyScheduleId(int dailyScheduleId);

	public List<Lecture> findAll();
}
