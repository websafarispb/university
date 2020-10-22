package ru.stepev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import ru.stepev.dao.TeacherDao;
import ru.stepev.model.Teacher;

@Component
public class TeacherService {

	private TeacherDao teacherDao;

	public TeacherService(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	public void add(Teacher teacher) {
		if (!isTeacherExist(teacher)) {
			teacherDao.create(teacher);
		}
	}

	private boolean isTeacherExist(Teacher teacher) {
		return teacherDao.findById(teacher.getId()).isPresent();
	}

	public void update(Teacher teacher) {
		if (isTeacherExist(teacher)) {
			teacherDao.update(teacher);
		}
	}

	public void delete(Teacher teacher) {
		if (isTeacherExist(teacher)) {
			teacherDao.delete(teacher.getId());
		}
	}

	public Optional<Teacher> getById(int teacherId) {
		return teacherDao.findById(teacherId);
	}

	public List<Teacher> getAll() {
		return teacherDao.findAll();
	}
}
