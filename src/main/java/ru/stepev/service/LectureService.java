package ru.stepev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import ru.stepev.dao.ClassroomDao;
import ru.stepev.dao.CourseDao;
import ru.stepev.dao.DailyScheduleDao;
import ru.stepev.dao.GroupDao;
import ru.stepev.dao.LectureDao;
import ru.stepev.dao.TeacherDao;
import ru.stepev.model.Classroom;
import ru.stepev.model.Course;
import ru.stepev.model.Group;
import ru.stepev.model.Lecture;
import ru.stepev.model.Teacher;

@Component
public class LectureService {

	private LectureDao lectureDao;
	private DailyScheduleDao dailyScheduleDao;
	private CourseDao courseDao;
	private ClassroomDao classroomDao;
	private GroupDao groupDao;
	private TeacherDao teacherDao;

	public LectureService(DailyScheduleDao dailyScheduleDao, CourseDao courseDao, ClassroomDao classroomDao,
			GroupDao groupDao, TeacherDao teacherDao, LectureDao lectureDao) {
		this.dailyScheduleDao = dailyScheduleDao;
		this.courseDao = courseDao;
		this.classroomDao = classroomDao;
		this.groupDao = groupDao;
		this.teacherDao = teacherDao;
		this.lectureDao = lectureDao;
	}

	public void add(Lecture lecture) {
		if (!isLectureExist(lecture) && checkAllFieldsOfLectureForExist(lecture) && isClassroomFree(lecture) && isGroupFree(lecture)
				&& isTeacherFree(lecture) && isTeacherCanTeachCourse(lecture.getTeacher(), lecture.getCourse())
				&& isGroupCanStudyCourse(lecture.getGroup(), lecture.getCourse())
				&& isClassroomHasQuiteCapacity(lecture.getClassRoom(), lecture.getGroup())) {
			lectureDao.create(lecture);
		}
	}

	private boolean isGroupFree(Lecture lecture) {
		return lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(),
				lecture.getTime(), lecture.getTime().plusHours(1), lecture.getGroup().getId()).isEmpty();
	}

	private boolean isClassroomFree(Lecture lecture) {
		return lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(),
				lecture.getTime(), lecture.getTime().plusHours(1), lecture.getClassRoom().getId()).isEmpty();
	}

	private boolean isTeacherFree(Lecture lecture) {
		return lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(),
				lecture.getTime(), lecture.getTime().plusHours(1), lecture.getTeacher().getId()).isEmpty();
	}

	public boolean isClassroomHasQuiteCapacity(Classroom classRoom, Group group) {
		return classRoom.getCapacity() >= group.getStudents().size();
	}

	public boolean isGroupCanStudyCourse(Group group, Course course) {
		return  groupDao.findByGroupIdAndCourseId(group.getId(), course.getId()).isPresent();
	}

	public boolean isTeacherCanTeachCourse(Teacher teacher, Course course) {
		return teacher.getCourses().contains(course);
	}

	public boolean checkAllFieldsOfLectureForExist(Lecture lecture) {
		boolean dailyScheduleExist = dailyScheduleDao.findById(lecture.getDailyScheduleId()).isPresent();
		boolean courseExist = courseDao.findById(lecture.getCourse().getId()).isPresent();
		boolean classroomExist = classroomDao.findById(lecture.getClassRoom().getId()).isPresent();
		boolean groupExist = groupDao.findById(lecture.getGroup().getId()).isPresent();
		boolean teacherExist = teacherDao.findById(lecture.getTeacher().getId()).isPresent();
		return dailyScheduleExist && courseExist && classroomExist && groupExist && teacherExist;
	}

	public boolean isLectureExist(Lecture lecture) {
		return lectureDao.findById(lecture.getId()).isPresent();
	}

	public void update(Lecture lecture) {
		if (isLectureExist(lecture) && checkAllFieldsOfLectureForExist(lecture) && !isClassroomFree(lecture) && !isGroupFree(lecture)
				&& !isTeacherFree(lecture) && isTeacherCanTeachCourse(lecture.getTeacher(), lecture.getCourse())
				&& isGroupCanStudyCourse(lecture.getGroup(), lecture.getCourse())
				&& isClassroomHasQuiteCapacity(lecture.getClassRoom(), lecture.getGroup())) {
			lectureDao.update(lecture);
		}
	}

	public void delete(Lecture lecture) {
		if (isLectureExist(lecture)) {
			lectureDao.delete(lecture.getId());
		}
	}

	public Optional<Lecture> getById(int lectureId) {
		return lectureDao.findById(lectureId);
	}

	public List<Lecture> getByDailyScheduleId(int dailyScheduleId) {
		return lectureDao.findByDailyScheduleId(dailyScheduleId);
	}

	public List<Lecture> getAll() {
		return lectureDao.findAll();
	}
}
