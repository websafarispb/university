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
		if (!isCourseExist(course) && isTeacherCanTheachCourse(course)) {
			courseDao.create(course);
			log.debug("Course with name {} was created", course.getName());
		} else {
			log.warn("Course with name {} is already exist", course.getName());
			throw new EntityAlreadyExistException(String.format(
					"Can not create course with name %s course already exist", course.getName()));
		}
	}

	public void update(Course course) {
		if (isCourseExist(course) && isTeacherCanTheachCourse(course)) {
			courseDao.update(course);
			log.debug("Course with name {} was updated", course.getName());
		} else {
			log.warn("Course with name {} doesn't exist", course.getName());
			throw new EntityNotFoundException(String.format(
					"Can not update course with name %s course doesn't exist", course.getName()));
		}
	}

	public void delete(Course course) {
		if (isCourseExist(course)) {
			courseDao.delete(course.getId());
			log.debug("Course with name {} was deleted", course.getName());
		}  else {
			log.warn("Course with name {} doesn't exist", course.getName());
			throw new EntityNotFoundException(String.format(
					"Can not deleted course with name %s course doesn't exist", course.getName()));
		}
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

	private boolean isTeacherCanTheachCourse(Course course) {
		return teacherDao.findAll().stream().filter(t -> t.getCourses().contains(course)).collect(toList()).size() > 0;
	}

	private boolean isCourseExist(Course course) {
		log.debug("Is course with name {} exis?", course.getName());
		return courseDao.findById(course.getId()).isPresent();
	}
}
