package ru.stepev.uinterface;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;

import ru.stepev.config.UniversityConfig;
import ru.stepev.model.*;
import ru.stepev.utils.*;

import static java.util.stream.Collectors.joining;

import java.io.IOException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import ru.stepev.dao.*;

public class UserInterface {

	private Map<String, String> menu;
	private Scanner scanner;
	private DataHelper dataHelper;
	private University university;
	private FileReader reader = new FileReader();
	private JdbcTemplate jdbcTemplate;
	private CourseDao courseDao;
	private GroupDao groupDao;
	private StudentDao studentDao;
	private TeacherDao teacherDao;
	private ClassroomDao classroomDao;
	private LectureDao lectureDao;
	private DailyScheduleDao dailyScheduleDao;

	public UserInterface() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UniversityConfig.class);

		jdbcTemplate = context.getBean("getJdbcTamplate", JdbcTemplate.class);
		courseDao = context.getBean("getCourseDao", CourseDao.class);
		studentDao = context.getBean("getStudentDao", StudentDao.class);
		teacherDao = context.getBean("getTeacherDao", TeacherDao.class);
		groupDao = context.getBean("getGroupDao", GroupDao.class);
		classroomDao = context.getBean("getClassroomDao", ClassroomDao.class);
		lectureDao = context.getBean("getLectureDao", LectureDao.class);
		dailyScheduleDao = context.getBean("getDailyScheduleDao", DailyScheduleDao.class);
		context.close();

		String sqlRequest = null;
		try {
			sqlRequest = reader.read("schema.sql").collect(joining(System.lineSeparator()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		jdbcTemplate.execute(sqlRequest);

		menu = new LinkedHashMap<>();
		scanner = new Scanner(System.in);
		menu.put("a", "a. Show schedule for teacher");
		menu.put("b", "b. Show schedule for student");

		dataHelper = new DataHelper();
		university = new University();

		courseDao.createCourses(dataHelper.createCourses());
		university.setCourses(courseDao.findAllCourses());

		classroomDao.createClassrooms(dataHelper.createClassRooms());
		university.setClassRooms(classroomDao.findAllCourses());

		groupDao.createGroups(dataHelper.createGroups());
		university.setGroups(groupDao.findAll());

		teacherDao.createTeachers(dataHelper.createTeachers());
		university.setTeachers(teacherDao.findAllTeacher());
		for (Teacher teacher : teacherDao.findAllTeacher()) {
			teacher.setCourses(courseDao.findAllCourses());
			teacherDao.assignToCourse(teacher);
		}

		studentDao.createStudents(dataHelper.createStudents());
		university.setStudents(studentDao.findAllStudents());

		for (Student student : studentDao.findAllStudents()) {
			studentDao.assignToCourse(student, courseDao.findAllCourses());
		}

		AtomicInteger counter = new AtomicInteger(0);
		studentDao.findAllStudents().stream()
				.forEach(s -> university.getGroups().get((counter.getAndIncrement()) % 10).addStudent(s));

		for (Group group : groupDao.findAll()) {
			groupDao.assignToStudents(group, university.getGroup(group.getName()).getStudents());
		}

		university.createDailySchedules();
		for (DailySchedule dailySchedule : university.getDailySchedules()) {
			dailyScheduleDao.create(dailySchedule);
			lectureDao.createLectures(dailySchedule.getLectures());
		}

		for (DailySchedule dailySchedule : dailyScheduleDao.findAllDailySchedules()) {
			dailySchedule.setLectures(lectureDao.findLecturesByDate(dailySchedule.getDate()));
			dailyScheduleDao.assignLectures(dailySchedule);
		}
	}

	public String getMenu() {
		return menu.values().stream().collect(joining(System.lineSeparator()));
	}

	public String choseMenuItem(String item) {
		String formattedAnswer = null;
		try {
			switch (item) {
			case "a":
				formattedAnswer = getScheduleForTeacher();
				break;
			case "b":
				formattedAnswer = getScheduleForStudent();
				break;
			default:
				formattedAnswer = "You should enter letter from a to f ";
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return formattedAnswer;
	}

	public String getScheduleForStudent() {
		studentDao.findAllStudents().stream().forEach(System.out::println);
		List<String> firstAndLastName = getFirstAndLastName();
		studentDao.findStudentIdByFirstAndLastNames(firstAndLastName.get(0), firstAndLastName.get(1)).stream()
				.forEach(System.out::println);
		List<Group> groups = groupDao.getGroupByStudentId(getId());
		return dailyScheduleDao.findSchedualesForStudent(groups.get(0), getPeriodOfTime()).stream()
				.map(DailySchedule::toString).collect(joining(System.lineSeparator()));
	}

	public String getScheduleForTeacher() {
		university.getTeachers().stream().forEach(System.out::println);
		return dailyScheduleDao.findSchedualesForTeacher(getId(), getPeriodOfTime()).stream()
				.map(DailySchedule::toString).collect(joining(System.lineSeparator()));
	}

	public List<LocalDate> getPeriodOfTime() {
		List<LocalDate> period = new ArrayList<>();
		System.out.println("Enter period or day with you want to get.");
		System.out.println("If you need only one day then first day and last day of schedule have to be same.");
		System.out.println("You have to enter day in the next format - \"yyyy-mm-dd\"");
		System.out.println("Enter the firest day of period");
		LocalDate firstDay = LocalDate.parse(scanner.nextLine());
		System.out.println("Enter the last day of period");
		LocalDate lastDay = LocalDate.parse(scanner.nextLine());
		if (!firstDay.equals(lastDay)) {
			period = Stream.iterate(firstDay, date -> date.plusDays(1))
					.limit(ChronoUnit.DAYS.between(firstDay, lastDay)).collect(Collectors.toList());
		}
		period.add(lastDay);
		return period;
	}

	public int getId() {
		System.out.println("Enter person ID  wich you need");
		return Integer.parseInt(scanner.nextLine());
	}

	public List<String> getFirstAndLastName() {
		List<String> name = new ArrayList<>();
		System.out.println("Enter first name");
		name.add(scanner.nextLine());
		System.out.println("Enter last name");
		name.add(scanner.nextLine());
		return name;
	}

}
