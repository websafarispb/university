package ru.stepev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import ru.stepev.dao.ClassroomDao;
import ru.stepev.dao.CourseDao;
import ru.stepev.dao.DailyScheduleDao;
import ru.stepev.dao.GroupDao;
import ru.stepev.dao.LectureDao;
import ru.stepev.dao.TeacherDao;
import ru.stepev.exception.EntityAlreadyExistException;
import ru.stepev.exception.EntityNotFoundException;
import ru.stepev.model.Classroom;
import ru.stepev.model.Course;
import ru.stepev.model.Group;
import ru.stepev.model.Lecture;
import ru.stepev.model.Teacher;

@Component
@Slf4j
public class LectureService {

	private LectureDao lectureDao;
	private DailyScheduleDao dailyScheduleDao;
	private CourseDao courseDao;
	private ClassroomDao classroomDao;
	private GroupDao groupDao;
	private TeacherDao teacherDao;

	@Value("${durationOfLecture}")
	private int durationOfLecture;

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
		if (!isLectureExist(lecture) && checkAllFieldsOfLectureForExist(lecture) && isClassroomFree(lecture)
				&& isGroupFree(lecture) && isTeacherFree(lecture)
				&& isTeacherCanTeachCourse(lecture.getTeacher(), lecture.getCourse())
				&& isGroupCanStudyCourse(lecture.getGroup(), lecture.getCourse())
				&& isClassroomHasQuiteCapacity(lecture.getClassRoom(), lecture.getGroup())) {
			lectureDao.create(lecture);
			log.debug("Lecture with time {} was created", lecture.getTime());

		} else {
			log.warn("Lecture with time {} is already exist", lecture.getTime());
			throw new EntityAlreadyExistException(
					String.format("Can not create lecture with time %s lecture already exist", lecture.getTime()));
		}
	}

	public boolean checkAllFieldsOfLectureForExist(Lecture lecture) {
		log.debug("Check filds of lecture with time {}", lecture.getTime());
		boolean dailyScheduleExist = dailyScheduleDao.findById(lecture.getDailyScheduleId()).isPresent();
		boolean courseExist = courseDao.findById(lecture.getCourse().getId()).isPresent();
		boolean classroomExist = classroomDao.findById(lecture.getClassRoom().getId()).isPresent();
		boolean groupExist = groupDao.findById(lecture.getGroup().getId()).isPresent();
		boolean teacherExist = teacherDao.findById(lecture.getTeacher().getId()).isPresent();
		return dailyScheduleExist && courseExist && classroomExist && groupExist && teacherExist;
	}

	public boolean isLectureExist(Lecture lecture) {
		log.debug("Is lecture with time {} exist?", lecture.getTime());
		return lectureDao.findById(lecture.getId()).isPresent();
	}

	public void update(Lecture lecture) {
		if (isLectureExist(lecture) && checkAllFieldsOfLectureForExist(lecture) && isClassroomFree(lecture)
				&& isGroupFree(lecture) && isTeacherFree(lecture)
				&& isTeacherCanTeachCourse(lecture.getTeacher(), lecture.getCourse())
				&& isGroupCanStudyCourse(lecture.getGroup(), lecture.getCourse())
				&& isClassroomHasQuiteCapacity(lecture.getClassRoom(), lecture.getGroup())) {
			lectureDao.update(lecture);
			log.debug("Lecture with time {} was updated", lecture.getTime());
		} else {
			log.warn("Lecture with time {} doesn't exist", lecture.getTime());
			throw new EntityNotFoundException(
					String.format("Can not update lecture with time %s lecture doesn't exist", lecture.getTime()));
		}
	}

	public void delete(Lecture lecture) {
		if (isLectureExist(lecture)) {
			lectureDao.delete(lecture.getId());
			log.debug("Lecture with time {} was deleted", lecture.getTime());
		} else {
			log.warn("Lecture with time {} doesn't exist", lecture.getTime());
			throw new EntityNotFoundException(
					String.format("Can not delete Lecture with time %s lecture doesn't exist", lecture.getTime()));
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

	private boolean isGroupFree(Lecture lecture) {
		log.debug("Is group on time {} free?", lecture.getTime());
		return lectureDao.findByDailyScheduleIdAndTimeAndGroupId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(durationOfLecture), lecture.getGroup().getId()).isEmpty();
	}

	private boolean isClassroomFree(Lecture lecture) {
		log.debug("Is classroom on time {} free?", lecture.getTime());
		return lectureDao.findByDailyScheduleIdAndTimeAndClassroomId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(durationOfLecture), lecture.getClassRoom().getId()).isEmpty();
	}

	private boolean isTeacherFree(Lecture lecture) {
		log.debug("Is teacher on time {} free?", lecture.getTime());
		return lectureDao.findByDailyScheduleIdAndTimeAndTeacherId(lecture.getDailyScheduleId(), lecture.getTime(),
				lecture.getTime().plusMinutes(durationOfLecture), lecture.getTeacher().getId()).isEmpty();
	}

	private boolean isClassroomHasQuiteCapacity(Classroom classRoom, Group group) {
		log.debug("Is classroom has quite capacity?");
		return classRoom.getCapacity() >= group.getStudents().size();
	}

	private boolean isGroupCanStudyCourse(Group group, Course course) {
		log.debug("Is group can study course?");
		return groupDao.findByGroupIdAndCourseId(group.getId(), course.getId()).isPresent();
	}

	private boolean isTeacherCanTeachCourse(Teacher teacher, Course course) {
		log.debug("Is teacher can teach course?");
		return teacher.getCourses().contains(course);
	}
}
