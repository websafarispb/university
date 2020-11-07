package ru.stepev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.stepev.dao.StudentDao;
import ru.stepev.exception.EntityAlreadyExistException;
import ru.stepev.exception.EntityNotFoundException;
import ru.stepev.model.Student;

@Component
@Slf4j
public class StudentService {

	private StudentDao studentDao;

	public StudentService(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	public void add(Student student) {
		try {
			isStudentExist(student);
			log.warn("Student with name {} is already exist", student.getFirstName() + " " + student.getLastName());
			throw new EntityAlreadyExistException(
					String.format("Can not create student with name %s student already exist",
							student.getFirstName() + " " + student.getLastName()));

		} catch (EntityNotFoundException e) {
			studentDao.create(student);
			log.debug("Student with name {} was added", student.getFirstName() + " " + student.getLastName());
		}
	}

	public void update(Student student) {
		if (isStudentExist(student)) {
			studentDao.update(student);
			log.warn("Student with name {} was updated", student.getFirstName() + " " + student.getLastName());
		}
	}

	public void delete(Student student) {
		if (isStudentExist(student)) {
			studentDao.delete(student.getId());
			log.warn("Student with name {} was deleted", student.getFirstName() + " " + student.getLastName());
		}
	}

	public Optional<Student> getById(int studentId) {
		return studentDao.findById(studentId);
	}

	public List<Student> getAll() {
		return studentDao.findAll();
	}

	public List<Student> getByFirstAndLastNames(String firstName, String lastName) {
		return studentDao.findByFirstAndLastNames(firstName, lastName);
	}

	public List<Student> getByGroupId(int groupId) {
		return studentDao.findByGroupId(groupId);
	}

	private boolean isStudentExist(Student student) {
		if (studentDao.findById(student.getId()).isPresent()) {
			return true;
		} else {
			throw new EntityNotFoundException(
					String.format("Student with name %s doesn't exist", student.getFirstName() + " " + student.getLastName()));
		}
	}
}
