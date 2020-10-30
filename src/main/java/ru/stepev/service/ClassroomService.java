package ru.stepev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import ru.stepev.dao.ClassroomDao;
import ru.stepev.model.Classroom;

@Component
public class ClassroomService {

	private ClassroomDao classroomDao;

	public ClassroomService(ClassroomDao classroomDao) {
		this.classroomDao = classroomDao;
	}

	public void add(Classroom classroom) {
		if (!isClassroomExist(classroom.getId())) {
			classroomDao.create(classroom);
		}
	}

	public void update(Classroom classroom) {
		if (isClassroomExist(classroom.getId())) {
			classroomDao.update(classroom);
		}
	}

	public void delete(Classroom classroom) {
		if (isClassroomExist(classroom.getId())) {
			classroomDao.delete(classroom.getId());
		}
	}

	public Optional<Classroom> getById(int classroomId) {
		return classroomDao.findById(classroomId);
	}

	public List<Classroom> getAll() {
		return classroomDao.findAll();
	}

	private boolean isClassroomExist(int classroomId) {
		return classroomDao.findById(classroomId).isPresent();
	}
}
