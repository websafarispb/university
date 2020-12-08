package ru.stepev.uinterface;

import java.util.*;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ru.stepev.config.UniversityConfig;
import ru.stepev.model.Classroom;
import ru.stepev.model.Course;
import ru.stepev.model.DailySchedule;
import ru.stepev.model.Group;
import ru.stepev.model.Lecture;
import ru.stepev.model.Student;
import ru.stepev.model.Teacher;
import ru.stepev.service.ClassroomService;
import ru.stepev.service.CourseService;
import ru.stepev.service.DailyScheduleService;
import ru.stepev.service.GroupService;
import ru.stepev.service.LectureService;
import ru.stepev.service.StudentService;
import ru.stepev.service.TeacherService;

import static java.util.stream.Collectors.joining;

import java.time.LocalDate;
import java.time.LocalTime;

public class UserInterface {

	private Map<String, String> menu;
	private Scanner scanner;
	private ClassroomService classroomService;
	private CourseService courseService;
	private DailyScheduleService dailyScheduleService;
	private GroupService groupService;
	private LectureService lectureService;
	private StudentService studentService;
	private TeacherService teacherService;

	public UserInterface() {
		AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(UniversityConfig.class);
		classroomService = context.getBean("classroomService", ClassroomService.class);
		courseService = context.getBean("courseService", CourseService.class);
		dailyScheduleService = context.getBean("dailyScheduleService", DailyScheduleService.class);
		groupService = context.getBean("groupService", GroupService.class);
		lectureService = context.getBean("lectureService", LectureService.class);
		studentService = context.getBean("studentService", StudentService.class);
		teacherService = context.getBean("teacherService", TeacherService.class);
		menu = new LinkedHashMap<>();
		scanner = new Scanner(System.in);
		menu.put("a", "a. Show schedule for teacher");
		menu.put("b", "b. Add teacher");
		menu.put("c", "c. Update teacher");
		menu.put("d", "d. Delete teacher");
		menu.put("e", "e. Find teacher by name");
		menu.put("f", "f. Show schedule for student");
		menu.put("g", "g. Add student");
		menu.put("h", "h. Update student");
		menu.put("i", "i. Delete student");
		menu.put("j", "j. Find student by name");
		menu.put("k", "k. Show all classrooms");
		menu.put("l", "l. Show all dailyschedules");
		menu.put("m", "m. Show all groups");
		menu.put("n", "n. Show all courses");
		menu.put("o", "o. Show all lectures");
		menu.put("p", "p. Create lecture");

	}

	public String getMenu() {
		return menu.values().stream().collect(joining(System.lineSeparator()));
	}

	public String choseMenuItem(String item) {
		List<LocalDate> periodOfTime = new ArrayList<>();
		String formattedAnswer = null;
		try {
			switch (item) {
			case "a":
				teacherService.getAll().stream().forEach(System.out::println);
				periodOfTime = getPeriodOfTime();
				formattedAnswer = dailyScheduleService
						.getScheduleForTeacher(getId(), periodOfTime.get(0), periodOfTime.get(1)).stream()
						.map(DailySchedule::toString).collect(joining(System.lineSeparator()));
				break;
			case "b":
				formattedAnswer = "Add techer";
				break;
			case "c":
				teacherService.getAll();
				Teacher teacher = teacherService.getById(getId()).get();
				teacher.setFirstName("Жора");
				teacherService.update(teacher);
				formattedAnswer = "Update have been completed!!!";
				break;

			case "d":
				teacherService.getAll();
				Teacher teacherForDelete = teacherService.getById(getId()).get();
				teacherService.delete(teacherForDelete);
				formattedAnswer = "Delete have been completed!!!";
				break;
			case "e":
				teacherService.getAll();
				formattedAnswer = teacherService.getById(getId()).get().toString();
				break;
			case "f":
				studentService.getAll().stream().forEach(System.out::println);
				List<String> fullName = getFirstAndLastName();
				studentService.getByFirstAndLastNames(fullName.get(0), fullName.get(1));
				int id = getId();
				periodOfTime = getPeriodOfTime();
				formattedAnswer = dailyScheduleService
						.getScheduleForStudent(id, periodOfTime.get(0), periodOfTime.get(1)).stream()
						.map(DailySchedule::toString).collect(joining(System.lineSeparator()));
				break;
			case "g":
				formattedAnswer = "Add student!!!";
				break;
			case "h":
				studentService.getAll();
				Student student = studentService.getById(getId()).get();
				student.setFirstName("Жора");
				studentService.update(student);
				formattedAnswer = "Update have been completed!!!";
				break;
			case "i":

				studentService.getAll();
				Student studentForDelete = studentService.getById(getId()).get();
				studentService.delete(studentForDelete);
				formattedAnswer = "Delete have been completed!!!";
				break;
			case "j":

				studentService.getAll();
				formattedAnswer = studentService.getById(getId()).get().toString();
				break;
			case "k":
				formattedAnswer = classroomService.getAll().stream().map(Classroom::toString)
						.collect(joining(System.lineSeparator()));
				break;
			case "l":
				formattedAnswer = dailyScheduleService.getAll().stream().map(DailySchedule::toString)
						.collect(joining(System.lineSeparator()));
				break;
			case "m":
				formattedAnswer = groupService.getAll().stream().map(Group::toString)
						.collect(joining(System.lineSeparator()));
				break;
			case "n":
				formattedAnswer = courseService.getAll().stream().map(Course::toString)
						.collect(joining(System.lineSeparator()));
				break;
			case "o":
				formattedAnswer = lectureService.getAll().stream().map(Lecture::toString)
						.collect(joining(System.lineSeparator()));
				break;
			case "p":
				lectureService.add(getLectureFromUser());
				formattedAnswer = "create lecture";
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

	private Lecture getLectureFromUser() {
		// TODO Auto-generated method stub
		System.out.println("Enter date for lecture!!!");
		Lecture lecture = new Lecture();

		System.out.println("Enter day in the next format - \"yyyy-mm-dd\"");
		LocalDate day = LocalDate.parse(scanner.nextLine());

		DailySchedule dailyScheduleId = dailyScheduleService.getByDate(day).get();

		System.out.println("Enter time in the next format \"10:15:30\"");
		LocalTime time = LocalTime.parse(scanner.nextLine());

		System.out.println(
				courseService.getAll().stream().map(Course::toString).collect(joining(System.lineSeparator())));
		System.out.println("Enter course id - ");
		Course course = courseService.getById(scanner.nextInt()).get();

		System.out.println(
				classroomService.getAll().stream().map(Classroom::toString).collect(joining(System.lineSeparator())));
		System.out.println("Enter classroom id - ");

		Classroom classRoom = classroomService.getById(scanner.nextInt()).get();

		System.out
				.println(groupService.getAll().stream().map(Group::toString).collect(joining(System.lineSeparator())));
		System.out.println("Enter group id - ");

		Group group = groupService.getById(scanner.nextInt()).get();

		System.out.println(
				teacherService.getAll().stream().map(Teacher::toString).collect(joining(System.lineSeparator())));
		System.out.println("Enter teacher id - ");

		Teacher teacher = teacherService.getById(scanner.nextInt()).get();

		return new Lecture(0, dailyScheduleId.getId(), time, course, classRoom, group, teacher);
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
		period.add(firstDay);
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
