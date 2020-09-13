package ru.stepev.service;

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

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
import ru.stepev.model.Lecture;
import ru.stepev.model.Student;
import ru.stepev.model.Teacher;
import ru.stepev.model.University;
import ru.stepev.utils.FileReader;

public class UniversityService {
	
	private JdbcTemplate jdbcTemplate;
	private CourseDao courseDao;
	private GroupDao groupDao;
	private StudentDao studentDao;
	private TeacherDao teacherDao;
	private ClassroomDao classroomDao;
	private LectureDao lectureDao;
	private DailyScheduleDao dailyScheduleDao;
	private FileReader reader = new FileReader();
	
	public UniversityService() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UniversityConfig.class);

		jdbcTemplate = context.getBean("jdbcTamplate", JdbcTemplate.class);
		courseDao = context.getBean("courseDao", CourseDao.class);
		studentDao = context.getBean("studentDao", StudentDao.class);
		teacherDao = context.getBean("teacherDao", TeacherDao.class);
		groupDao = context.getBean("groupDao", GroupDao.class);
		classroomDao = context.getBean("classroomDao", ClassroomDao.class);
		lectureDao = context.getBean("lectureDao", LectureDao.class);
		dailyScheduleDao = context.getBean("dailyScheduleDao", DailyScheduleDao.class);
		context.close();
		String sqlRequest = null;
		try {
			sqlRequest = reader.read("schema.sql").collect(joining(System.lineSeparator()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		jdbcTemplate.execute(sqlRequest);
	}
	
	public String getScheduleForTeacher(int teacherId, List<LocalDate> periodOfTime) {
		return dailyScheduleDao.findSchedualesForTeacher(teacherId, periodOfTime).stream()
				.map(DailySchedule::toString).collect(joining(System.lineSeparator()));
	}
	
	public String getScheduleForStudent(int studentId, List<LocalDate> periodOfTime) {
		List<Group> groups = groupDao.getGroupByStudentId(studentId);
		return dailyScheduleDao.findSchedualesForStudent(groups.get(0), periodOfTime).stream()
				.map(DailySchedule::toString).collect(joining(System.lineSeparator()));
	}

	public void createSerives(University university) {
		createCourses(university.getCourses());
		createClassrooms(university.getClassRooms());
		createGroups(university.getGroups());
		createTeachers(university.getTeachers(), university.getCourses());
		createStudents(university.getStudents(), university.getCourses(),university.getGroups());
		createDailySchedules(university.getDailySchedules());
	}
	
	public void getTeachers() {
		teacherDao.findAllTeacher().stream().forEach(System.out::println);	
	}

	public void getStudents() {
		studentDao.findAllStudents().stream().forEach(System.out::println);
	}

	private void createDailySchedules(List<DailySchedule> dailySchedules) {
		for (DailySchedule dailySchedule : dailySchedules) {
			dailyScheduleDao.create(dailySchedule);
			for(Lecture lecture : dailySchedule.getLectures()) {
				lectureDao.create(lecture);
			}
		}

		for (DailySchedule dailySchedule : dailySchedules) {
			dailySchedule.setLectures(lectureDao.findLecturesByDate(dailySchedule.getDate()));
			dailyScheduleDao.assignLectures(dailySchedule);
		}	
	}

	private void createStudents(List<Student> students, List<Course> courses, List<Group> groups) {
		for (Student student : students) {
			studentDao.create(student);
			student.setCourses(courses);
			studentDao.assignToCourse(student);
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
			teacherDao.create(teacher);
			teacher.setCourses(courses);
			teacherDao.assignToCourse(teacher);
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
		studentDao.findStudentByFirstAndLastNames(firstAndLastName.get(0), firstAndLastName.get(1)).stream()
		.forEach(System.out::println);
	}

	public void deleteTeacherById(int teacherId) {
		teacherDao.delete(teacherId);
	}

	public void updateTeacherById(int teacherId) {
		Teacher teacher = new Teacher(333, "Peter", "Petrov", LocalDate.of(2000, 12, 2), "wer@mail.ru", Gender.MALE, "Street 56", null);
		teacherDao.update(teacher, teacherId);
		
	}

	public Teacher findTeacherById(int teacherId) {
		return teacherDao.findById(teacherId);
	}

	public void deleteStudentById(int studentId) {
		studentDao.delete(studentId);
	}

	public void updateStudentById(int studentId) {
		Student student = new Student(333, "Peter", "Petrov", LocalDate.of(2000, 12, 2), "wer@mail.ru", Gender.MALE, "Street 56", null);
		studentDao.update(student, studentId);	
	}

	public Object findStudentById(int studentId) {
		return studentDao.findById(studentId);
	}
}
