package ru.stepev.uinterface;

import java.util.*;

import ru.stepev.model.DailySchedule;
import ru.stepev.model.University;
import ru.stepev.utils.DataHelper;

import static java.util.stream.Collectors.joining;

import java.time.LocalDate;

public class UserInterface {

	private Map<String, String> menu;
	private Scanner scanner;
	private DataHelper dataHelper;
	private University university;

	public UserInterface() {
		menu = new LinkedHashMap<>();
		scanner = new Scanner(System.in);
		menu.put("a", "a. Show schedule for teacher");
		menu.put("b", "b. Show schedule for student");
		
		dataHelper = new DataHelper();
		university = new University();
		university.setCourses(dataHelper.createCourses());
		university.setTeachers(dataHelper.createTeachers());
		university.setStudents(dataHelper.createStudents());
		university.setGroups(dataHelper.createGroups());
		university.setClassRooms(dataHelper.createClassRooms());
		university.setDailySchedules(dataHelper.createDailySchedules());
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
		university.getStudents().stream().forEach(System.out::println);
		List<String> firstAndLastName = getFirstAndLastName();
		List<DailySchedule> schedule = university.getTimeTableForStudent(firstAndLastName.get(0), firstAndLastName.get(1), getPeriodOfTime());
		return schedule.stream().map(DailySchedule::toString).collect(joining(System.lineSeparator()));
	}

	public String getScheduleForTeacher() {
		university.getTeachers().stream().forEach(System.out::println);
		List<String> firstAndLastName = getFirstAndLastName();
		List<DailySchedule> schedule = university.getTimeTableForTeacher(firstAndLastName.get(0), firstAndLastName.get(1), getPeriodOfTime());
		return schedule.stream().map(DailySchedule::toString).collect(joining(System.lineSeparator()));
	}
	
	public List<LocalDate> getPeriodOfTime (){
		List<LocalDate> period = new ArrayList<>();
		System.out.println("Enter period or day with you want to get.");
		System.out.println("If you need only one day then first day and last day of schedule have to be same.");
		System.out.println("You have to enter day in the next format - \"yyyy-mm-dd\"");
		System.out.println("Enter the firest day of period");
		LocalDate firstDay = LocalDate.parse(scanner.nextLine());
		System.out.println("Enter the last day of period");
		LocalDate lastDay = LocalDate.parse(scanner.nextLine());
		if(!firstDay.equals(lastDay)) {
			period.add(firstDay);
			period.add(lastDay);
		}
		else
			period.add(firstDay);
		return period;
	}
	
	public List<String> getFirstAndLastName(){
		List<String> name = new ArrayList<>();
		System.out.println("Enter first name");
		name.add(scanner.nextLine());
		System.out.println("Enter last name");
		name.add(scanner.nextLine());
		return name;
	}
	
}
