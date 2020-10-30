package ru.stepev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import ru.stepev.dao.StudentDao;
import ru.stepev.model.Student;

@Component
public class StudentService {

	private StudentDao studentDao;

	public StudentService(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	public void add(Student student) {
		if (!isStudentExist(student)) {
			studentDao.create(student);
		}
	}

	public void update(Student student) {
		if (isStudentExist(student)) {
			studentDao.update(student);
		}
	}

	public void delete(Student student) {
		if (isStudentExist(student)) {
			studentDao.delete(student.getId());
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
		return studentDao.findById(student.getId()).isPresent();
	}
}
