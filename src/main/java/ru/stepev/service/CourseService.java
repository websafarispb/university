package ru.stepev.service;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.stepev.dao.CourseDao;
import ru.stepev.dao.TeacherDao;
import ru.stepev.exception.EntityAlreadyExistException;
import ru.stepev.exception.EntityNotFoundException;
import ru.stepev.exception.TecherIsNotAbleTheachCourseException;
import ru.stepev.model.Course;
import ru.stepev.model.Student;
import ru.stepev.model.Teacher;

@Component
@Slf4j
public class CourseService {

	private CourseDao courseDao;
	private TeacherDao teacherDao;

	public CourseService(CourseDao courseDao, TeacherDao teacherDao) {
		this.courseDao = courseDao;
		this.teacherDao = teacherDao;
	}

	public void add(Course course) {
		log.debug("Creating course {}", course.getName());
		try {
			isCourseNotExist(course);
			isTeacherCanTheachCourse(course);
			courseDao.create(course);
			log.debug("Course with name {} was created", course.getName());
		} catch (EntityAlreadyExistException e) {
			log.warn("Course with name {} is already exist", course.getName());
		}
	}

	public void update(Course course) {
		isCourseExist(course);
		isTeacherCanTheachCourse(course);
		courseDao.update(course);
		log.debug("Course with name {} was updated", course.getName());
	}

	public void delete(Course course) {
		isCourseExist(course);
		courseDao.delete(course.getId());
		log.debug("Course with name {} was deleted", course.getName());
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

	private void isTeacherCanTheachCourse(Course course) {
		if (teacherDao.findAll().stream().filter(t -> t.getCourses().contains(course)).collect(toList()).size() <= 0)
			throw new TecherIsNotAbleTheachCourseException(
					String.format("Teacher can't teach course name %s", course.getName()));
	}

	private void isCourseNotExist(Course course) {
		if (courseDao.findById(course.getId()).isPresent())
			throw new EntityAlreadyExistException(String.format("Course with name %s already exist", course.getName()));
	}

	private void isCourseExist(Course course) {
		if (courseDao.findById(course.getId()).isEmpty())
			throw new EntityNotFoundException(String.format("Course with name %s doesn't exist", course.getName()));
	}
}
