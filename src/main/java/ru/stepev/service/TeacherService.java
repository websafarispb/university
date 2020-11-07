package ru.stepev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.stepev.dao.TeacherDao;
import ru.stepev.exception.EntityAlreadyExistException;
import ru.stepev.exception.EntityNotFoundException;
import ru.stepev.model.Teacher;

@Component
@Slf4j
public class TeacherService {

	private TeacherDao teacherDao;

	public TeacherService(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	public void add(Teacher teacher) {
		try {
			isTeacherExist(teacher);
			log.warn("Teacher with name {} is already exist", teacher.getFirstName() + " " + teacher.getLastName());
			throw new EntityAlreadyExistException(
					String.format("Can not create teacher with name %s teacher already exist",
							teacher.getFirstName() + " " + teacher.getLastName()));

		} catch (EntityNotFoundException e) {
			teacherDao.create(teacher);
			log.warn("Teacher with name {} was added", teacher.getFirstName() + " " + teacher.getLastName());
		}
	}

	public void update(Teacher teacher) {
		if (isTeacherExist(teacher)) {
			teacherDao.update(teacher);
			log.warn("Teacher with name {} was updated", teacher.getFirstName() + " " + teacher.getLastName());
		}
	}

	public void delete(Teacher teacher) {
		if (isTeacherExist(teacher)) {
			teacherDao.delete(teacher.getId());
			log.warn("Teacher with name {} was deleted", teacher.getFirstName() + " " + teacher.getLastName());
		}
	}

	public Optional<Teacher> getById(int teacherId) {
		return teacherDao.findById(teacherId);
	}

	public List<Teacher> getAll() {
		return teacherDao.findAll();
	}

	private boolean isTeacherExist(Teacher teacher) {

		if (teacherDao.findById(teacher.getId()).isPresent()) {
			return true;
		} else {
			throw new EntityNotFoundException(String.format("Teacher with name %s doesn't exist",
					teacher.getFirstName() + " " + teacher.getLastName()));
		}
	}
}
