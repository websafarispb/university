package ru.stepev;

import java.util.*;

import ru.stepev.model.*;
import ru.stepev.utils.DataHelper;

public class Main {

	public static void main(String[] args) {
		DataHelper dataHelper = new DataHelper();
		Univercity univercity = new Univercity();
		univercity.setCourses(dataHelper.createCourses());
	//	univercity.getCourses().stream().forEach(System.out::println);
		univercity.setTeachers(dataHelper.createTeachers());
		univercity.getTeachers().stream().forEach(System.out::println);
		univercity.setStudents(dataHelper.createStudents());
	//	univercity.getStudents().stream().forEach(System.out::println);
		univercity.setGroups(dataHelper.createGroups());
	//	univercity.getGroups().stream().forEach(System.out::println);
		univercity.setClassRooms(dataHelper.createClassRooms());
	//	univercity.getClassRooms().stream().forEach(System.out::println);
		univercity.setDailySchedules(dataHelper.createDailySchedules());
	//	univercity.getDailySchedules().getSchedule().get(new GregorianCalendar(2020, 8, 20).getTime()).forEach(System.out::println);
		Scanner scanner = new Scanner(System.in);
		String firstName = scanner.nextLine();
		String lastName = scanner.nextLine();

		List <Date> periodOfTime = new ArrayList<>();
		periodOfTime.add(new GregorianCalendar(2020, 8, 19).getTime());
		periodOfTime.add(new GregorianCalendar(2020, 8, 20).getTime());
		//DailySchedule studentDailySchedules = univercity.getTimeTableForStudent(firstName, lastName, periodOfTime);
		DailySchedule studentDailySchedules = univercity.getTimeTableForTeacher(firstName, lastName, periodOfTime);
		//univercity.getGroupOfStudent(firstName, lastName).getStudents().stream().forEach(System.out::println);
		System.out.println(studentDailySchedules);
		scanner.close();
		
	}
}
