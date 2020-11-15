package ru.stepev.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.stepev.dao.ClassroomDao;
import ru.stepev.exception.EntityAlreadyExistException;
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
		checkClassroomNotExist(classroom);
		classroomDao.create(classroom);
		log.debug("Classroom with address {} was created", classroom.getAddress());

	}

	public void update(Classroom classroom) {
		checkClassroomExist(classroom);
		classroomDao.update(classroom);
		log.debug("Classroom with address {} was updated", classroom.getAddress());

	}

	public void delete(Classroom classroom) {
		checkClassroomExist(classroom);
		classroomDao.delete(classroom.getId());
		log.debug("Classroom with address {} was deleted", classroom.getAddress());

	}

	public Optional<Classroom> getById(int classroomId) {
		return classroomDao.findById(classroomId);
	}

	public List<Classroom> getAll() {
		return classroomDao.findAll();
	}

	public void checkClassroomNotExist(Classroom classroom) {
		if (classroomDao.findByAddress(classroom.getAddress()).isPresent()) {
			throw new EntityAlreadyExistException(String.format("Classroom with address %s already exist", classroom.getAddress()));
		}
	}

	public void checkClassroomExist(Classroom classroom) {
		if (classroomDao.findByAddress(classroom.getAddress()).isEmpty()) {
			throw new EntityNotFoundException(String.format("Classroom with address %s doesn't exist", classroom.getAddress()));
		}
	}
}
