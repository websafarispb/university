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
		verifyCourseIsUnique(course);
		courseDao.create(course);
		log.debug("Course with name {} was created", course.getName());
	}

	public void update(Course course) {
		verifyCourseIsExist(course);
		verifyCourseIsUnique(course);
		courseDao.update(course);
		log.debug("Course with name {} was updated", course.getName());
	}

	public void delete(Course course) {
		verifyCourseIsExist(course);
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

	public void verifyCourseIsUnique(Course course) {
		Optional<Course> existingCourse = courseDao.findByName(course.getName());
		if (existingCourse.isPresent() && (existingCourse.get().getId() != course.getId())) {
			throw new EntityAlreadyExistException(String.format("Course with name %s already exist", course.getName()));
		}
	}

	public void verifyCourseIsExist(Course course) {
		if (courseDao.findById(course.getId()).isEmpty()) {
			throw new EntityNotFoundException(String.format("Course with name %s doesn't exist", course.getName()));
		}
	}
}
