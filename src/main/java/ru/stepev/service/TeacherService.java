package ru.stepev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.stepev.dao.TeacherDao;
import ru.stepev.exception.EntityAlreadyExistException;
import ru.stepev.exception.EntityNotFoundException;
import ru.stepev.model.Student;
import ru.stepev.model.Teacher;

@Component
@Slf4j
public class TeacherService {

	private TeacherDao teacherDao;

	public TeacherService(TeacherDao teacherDao) {
		this.teacherDao = teacherDao;
	}

	public void add(Teacher teacher) {
		checkTeacherNotExist(teacher);
		teacherDao.create(teacher);
		log.debug("Teacher with name {} was added", teacher.getFirstName() + " " + teacher.getLastName());
	}

	public void update(Teacher teacher) {
		checkTeacherExist(teacher);
		teacherDao.update(teacher);
		log.debug("Teacher with name {} was updated", teacher.getFirstName() + " " + teacher.getLastName());
	}

	public void delete(Teacher teacher) {
		checkTeacherExist(teacher);
		teacherDao.delete(teacher.getId());
		log.debug("Teacher with name {} was deleted", teacher.getFirstName() + " " + teacher.getLastName());
	}

	public Optional<Teacher> getById(int teacherId) {
		return teacherDao.findById(teacherId);
	}

	public List<Teacher> getAll() {
		return teacherDao.findAll();
	}

	public void checkTeacherExist(Teacher teacher) {
		if (teacherDao.findById(teacher.getId()).isEmpty()) {
			throw new EntityNotFoundException(String.format("Teacher with ID %s doesn't exist",
					teacher.getId()));
		}
	}

	public void checkTeacherNotExist(Teacher teacher) {
		if (teacherDao.findById(teacher.getId()).isPresent()) {
			throw new EntityAlreadyExistException(String.format("Teacher with ID %s already exist",
					teacher.getId()));
		}
	}

	public int getNumberOfItems() {
		return teacherDao.findNumberOfItems() ;
	}

	public List<Teacher> getAndSortByFirstName(int numberOfItems, int offset) {
		return teacherDao.findAndSortByFirstName(numberOfItems, offset);
	}

	public List<Teacher> getAndSortByLastName(int numberOfItems, int offset) {
		return teacherDao.findAndSortByLastName(numberOfItems, offset);
	}

	public List<Teacher> getAndSortById(int numberOfItems, int offset) {
		return teacherDao.findAndSortById(numberOfItems, offset);
	}

	public List<Teacher> getAndSortByBirthday(int numberOfItems, int offset) {
		return teacherDao.findAndSortByBirthday(numberOfItems, offset);
	}

	public List<Teacher> getAndSortByEmail(int numberOfItems, int offset) {
		return teacherDao.findAndSortByEmail(numberOfItems, offset);
	}

	public List<Teacher> getAndSortByAddress(int numberOfItems, int offset) {
		return teacherDao.findAndSortByAddress(numberOfItems, offset);
	}
}
