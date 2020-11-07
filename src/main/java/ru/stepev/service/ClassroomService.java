package ru.stepev.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.stepev.dao.ClassroomDao;
import ru.stepev.exception.EntityNotFoundException;
import ru.stepev.model.Classroom;

@Component
@Slf4j
public class ClassroomService {

	private ClassroomDao classroomDao;

	public ClassroomService(ClassroomDao classroomDao) {
		this.classroomDao = classroomDao;
	}

	public void add(Classroom classroom) {
		log.debug("Creating classroom with address {}", classroom.getAddress());
		try {
			isClassroomExist(classroom.getId());
			log.warn("Classroom with address {} was not created", classroom.getAddress());
		} catch (EntityNotFoundException e) {
			classroomDao.create(classroom);
			log.debug("Classroom with address {} was created", classroom.getAddress());
		}
	}

	public void update(Classroom classroom) {
		log.debug("Updating classroom with address {}", classroom.getAddress());
		if (isClassroomExist(classroom.getId())) {
			classroomDao.update(classroom);
			log.debug("Classroom with address {} was updated", classroom.getAddress());
		}
	}

	public void delete(Classroom classroom) {
		log.debug("Delete classroom with address {}", classroom.getAddress());
		if (isClassroomExist(classroom.getId())) {
			classroomDao.delete(classroom.getId());
			log.debug("Classroom with address {} was deleted", classroom.getAddress());
		}
	}

	public Optional<Classroom> getById(int classroomId) {
		return classroomDao.findById(classroomId);
	}

	public List<Classroom> getAll() {
		return classroomDao.findAll();
	}

	private boolean isClassroomExist(int classroomId) {
		if (classroomDao.findById(classroomId).isPresent())
			return true;
		else
			throw new EntityNotFoundException(String.format("Classroom with ID %s doesn't exist", classroomId));
	}
}
