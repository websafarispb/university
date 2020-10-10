package ru.stepev.uinterface;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import ru.stepev.service.UniversityService;

import static java.util.stream.Collectors.joining;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class UserInterface {

	private Map<String, String> menu;
	private Scanner scanner;
	private UniversityService universityService;

	public UserInterface() {
		menu = new LinkedHashMap<>();
		scanner = new Scanner(System.in);
		menu.put("a", "a. Show schedule for teacher");
		menu.put("b", "b. Show schedule for student");
		menu.put("c", "c. Delete teacher");
		menu.put("d", "d. Update teacher");
		menu.put("e", "e. Find teacher by ID");
		menu.put("f", "f. Delete student");
		menu.put("g", "g. Update student");
		menu.put("h", "h. Find student by ID");

		universityService = new UniversityService();
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
				universityService.getTeachers();
				periodOfTime = getPeriodOfTime();
				formattedAnswer = universityService.getScheduleForTeacher(getId(), periodOfTime.get(0), periodOfTime.get(1));
				break;
			case "b":
				universityService.getStudents();
				universityService.getStudentsByFirstAndLastNames(getFirstAndLastName());
				int id = getId();
				periodOfTime = getPeriodOfTime();
				formattedAnswer = universityService.getScheduleForStudent(id, periodOfTime.get(0), periodOfTime.get(1));
				break;
			case "c":
				universityService.getTeachers();
				universityService.deleteTeacherById(getId());
				formattedAnswer = "Delete have been completed!!!";
				break;
			case "d":
				universityService.getTeachers();
				universityService.updateTeacherById(getId());
				formattedAnswer = "Update have been completed!!!";
				break;
			case "e":
				universityService.getTeachers();
				formattedAnswer = universityService.findTeacherById(getId()).toString();
				break;
			case "f":
				universityService.getStudents();
				universityService.deleteStudentById(getId());
				formattedAnswer = "Delete have been completed!!!";
				break;
			case "g":
				universityService.getStudents();
				universityService.updateStudentById(getId());
				formattedAnswer = "Update have been completed!!!";
				break;
			case "h":
				universityService.getStudents();
				formattedAnswer = universityService.findStudentById(getId()).toString();
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
