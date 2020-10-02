package ru.stepev.service;

import static java.util.stream.Collectors.joining;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.stepev.config.UniversityConfig;
import ru.stepev.dao.ClassroomDao;
import ru.stepev.dao.CourseDao;
import ru.stepev.dao.DailyScheduleDao;
import ru.stepev.dao.GroupDao;
import ru.stepev.dao.LectureDao;
import ru.stepev.dao.StudentDao;
import ru.stepev.dao.TeacherDao;
import ru.stepev.model.Classroom;
import ru.stepev.model.Course;
import ru.stepev.model.DailySchedule;
import ru.stepev.model.Gender;
import ru.stepev.model.Group;
import ru.stepev.model.Student;
import ru.stepev.model.Teacher;

public class UniversityService {
	
	private CourseDao courseDao;
	private GroupDao groupDao;
	private StudentDao studentDao;
	private TeacherDao teacherDao;
	private ClassroomDao classroomDao;
	private LectureDao lectureDao;
	private DailyScheduleDao dailyScheduleDao;
	
	public UniversityService() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UniversityConfig.class);
		courseDao = context.getBean("courseDao", CourseDao.class);
		studentDao = context.getBean("studentDao", StudentDao.class);
		teacherDao = context.getBean("teacherDao", TeacherDao.class);
		groupDao = context.getBean("groupDao", GroupDao.class);
		classroomDao = context.getBean("classroomDao", ClassroomDao.class);
		lectureDao = context.getBean("lectureDao", LectureDao.class);
		dailyScheduleDao = context.getBean("dailyScheduleDao", DailyScheduleDao.class);
		context.close();
	}
	
	public String getScheduleForTeacher(int teacherId, List<LocalDate> periodOfTime) {
		return dailyScheduleDao.findBiTeacherIdAndPeriodOfTime(teacherId, periodOfTime).stream()
				.map(DailySchedule::toString).collect(joining(System.lineSeparator()));
	}
	
	public String getScheduleForStudent(int studentId, List<LocalDate> periodOfTime) {
		List<Group> groups = groupDao.findByStudentId(studentId);
		return dailyScheduleDao.findByGroupAndPeriodOfTime(groups.get(0), periodOfTime).stream()
				.map(DailySchedule::toString).collect(joining(System.lineSeparator()));
	}
	
	public void getTeachers() {
		teacherDao.findAll().stream().forEach(System.out::println);	
	}

	public void getStudents() {
		studentDao.findAll().stream().forEach(System.out::println);
	}

	private void createDailySchedules(List<DailySchedule> dailySchedules) {
		for (DailySchedule dailySchedule : dailySchedules) {
			dailyScheduleDao.create(dailySchedule);
		}
	}

	private void createStudents(List<Student> students, List<Course> courses, List<Group> groups) {
		for (Student student : students) {
			student.setCourses(courses);
			studentDao.create(student);
		}	
		AtomicInteger counter = new AtomicInteger(0);
		students.stream()
				.forEach(s -> groups.get((counter.getAndIncrement()) % 10).addStudent(s));
		for (Group group : groups) {
			groupDao.assignToStudents(group, group.getStudents());
		}
	}

	private void createTeachers(List<Teacher> teachers, List<Course> courses) {
		for (Teacher teacher : teachers) {
			teacher.setCourses(courses);
			teacherDao.create(teacher);
		}	
	}

	private void createGroups(List<Group> groups) {
		for (Group group : groups) {
			groupDao.create(group);
		}	
	}

	private void createClassrooms(List<Classroom> classrooms) {
		for (Classroom classroom : classrooms) {
			classroomDao.create(classroom);
		}		
	}

	private void createCourses(List<Course> courses) {
		for(Course course : courses) {
			courseDao.create(course);
		}
	}

	public void getStudentsByFirstAndLastNames(List<String> firstAndLastName) {
		studentDao.findByFirstAndLastNames(firstAndLastName.get(0), firstAndLastName.get(1)).stream()
		.forEach(System.out::println);
	}

	public void deleteTeacherById(int teacherId) {
		teacherDao.delete(teacherId);
	}

	public void updateTeacherById(int teacherId) {
		Teacher teacher = new Teacher(333, "Peter", "Petrov", LocalDate.of(2000, 12, 2), "wer@mail.ru", Gender.MALE, "Street 56", null);
		teacherDao.update(teacher);
		
	}

	public Teacher findTeacherById(int teacherId) {
		return teacherDao.findById(teacherId);
	}

	public void deleteStudentById(int studentId) {
		studentDao.delete(studentId);
	}

	public void updateStudentById(int studentId) {
		Student student = new Student(333, "Peter", "Petrov", LocalDate.of(2000, 12, 2), "wer@mail.ru", Gender.MALE, "Street 56", null);
		studentDao.update(student);	
	}

	public Object findStudentById(int studentId) {
		return studentDao.findById(studentId);
	}
}
