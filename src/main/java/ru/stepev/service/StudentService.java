package ru.stepev.service;

import static java.util.function.Predicate.not;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.stepev.dao.CourseDao;
import ru.stepev.dao.StudentDao;
import ru.stepev.exception.EntityAlreadyExistException;
import ru.stepev.exception.EntityNotFoundException;
import ru.stepev.model.Course;
import ru.stepev.model.Student;
import ru.stepev.utils.Paginator;

@Component
@Slf4j
public class StudentService {

	private StudentDao studentDao;
	private CourseDao courseDao;

	public StudentService(StudentDao studentDao, CourseDao courseDao) {
		this.studentDao = studentDao;
		this.courseDao = courseDao;
	}

	public void add(Student student) {
		verifyStudentNotExist(student);
		verifyCoursesOfStudentForExist(student);
		studentDao.create(student);
		log.debug("Student with name {} was added", student.getFirstName() + " " + student.getLastName());
	}

	public void update(Student student) {
		verifyStudentExist(student);
		verifyCoursesOfStudentForExist(student);
		studentDao.update(student);
		log.debug("Student with name {} was updated", student.getFirstName() + " " + student.getLastName());
	}

	public void delete(Student student) {
		verifyStudentExist(student);
		studentDao.delete(student.getId());
		log.debug("Student with name {} was deleted", student.getFirstName() + " " + student.getLastName());
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

	public void verifyStudentExist(Student student) {
		if (studentDao.findById(student.getId()).isEmpty()) {
			throw new EntityNotFoundException(String.format("Student with ID %s doesn't exist", student.getId()));
		}
	}

	public void verifyStudentNotExist(Student student) {
		if (studentDao.findById(student.getId()).isPresent()) {
			throw new EntityAlreadyExistException(String.format("Student with ID %s already exist",
					student.getId()));
		}
	}
	
	private void verifyCoursesOfStudentForExist(Student student) {
		List<Course> courses = courseDao.findAll();
		student.getCourses().stream().filter(not(courses::contains)).forEach(c -> {
			throw new EntityNotFoundException(String.format("Course with name %s doesn't exist", c.getName()));
		});
	}

	public int count() {
		return studentDao.findNumberOfItems() ;
	}

	public List<Student> getAndSortByFirstName(int numberOfItems, int offset) {
		return studentDao.findAndSortByFirstName(numberOfItems, offset);
	}

	public List<Student> getAndSortByLastName(int numberOfItems, int offset) {
		return studentDao.findAndSortByLastName(numberOfItems, offset);
	}

	public List<Student> getAndSortById(int numberOfItems, int offset) {
		return studentDao.findAndSortById(numberOfItems, offset);
	}

	public List<Student> getAndSortByBirthday(int numberOfItems, int offset) {
		return studentDao.findAndSortByBirthday(numberOfItems, offset);
	}

	public List<Student> getAndSortByEmail(int numberOfItems, int offset) {
		return studentDao.findAndSortByEmail(numberOfItems, offset);
	}

	public List<Student> getAndSortByAddress(int numberOfItems, int offset) {
		return studentDao.findAndSortByAddress(numberOfItems, offset);
	}

	public List<Student> getAndSort(Paginator paginator) {
		switch(paginator.getSortBy()) {
			case ("First_name") : return getAndSortByFirstName(paginator.getItemsPerPage(), paginator.getOffset());
			case ("Last_name")  : return getAndSortByLastName(paginator.getItemsPerPage(), paginator.getOffset()); 
			case ("Birthday")  : return getAndSortByBirthday(paginator.getItemsPerPage(), paginator.getOffset()); 
			case ("Email")  : return getAndSortByEmail(paginator.getItemsPerPage(), paginator.getOffset()); 
			case ("Address")  : return getAndSortByAddress(paginator.getItemsPerPage(), paginator.getOffset());
			default : return getAndSortByLastName(paginator.getItemsPerPage(), paginator.getOffset()); 
		}
	}
}
