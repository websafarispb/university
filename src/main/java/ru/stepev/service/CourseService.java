package ru.stepev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import ru.stepev.dao.CourseDao;
import ru.stepev.model.Course;
import ru.stepev.model.Student;
import ru.stepev.model.Teacher;

@Component
public class CourseService {

	private CourseDao courseDao;

	public CourseService(CourseDao courseDao) {
		this.courseDao = courseDao;
	}

	public void add(Course course) {
		courseDao.create(course);
	}

	public void update(Course course) {
		courseDao.update(course);
	}
	
	public void delete( int courseId) {
		courseDao.delete(courseId);
	}

	public Optional<Course> getById(int courseId) {
		return courseDao.findById(courseId);
	}

	public List<Course> getAll() {
		return courseDao.findAll();
	}

	public List<Course> getByTeacher(Teacher teacher) {
		return courseDao.findByTeacherId(teacher.getId());
	}

	public List<Course> getByStudent(Student student) {
		return courseDao.findByStudentId(student.getId());
	}

}
