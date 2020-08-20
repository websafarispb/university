package ru.stepev.utils;

import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import ru.stepev.model.*;

import static java.util.stream.Collectors.toList;

public class DataHelper {

	private Random random = new Random();
	private List<Group> groups = new ArrayList<>();
	private List<Student> students = new ArrayList<>();
	private List<Teacher> teachers = new ArrayList<>();
	private List<Course> courses = new ArrayList<>();
	private List<ClassRoom> classRooms = new ArrayList<>();
	private DailySchedule dailySchedules = new DailySchedule();
	

	public List<Lecture> createLectures(Date date) {
		List<Lecture> lectures = new ArrayList<>();
		for (int j = 0; j < groups.size(); j++) {
			List<Integer> freeRooms = random.ints(0, 10).distinct().limit(10).boxed().collect(toList());
			List<Integer> freeTeachers = random.ints(0, 10).distinct().limit(10).boxed().collect(toList());
			for (int i = 0; i < 10; i++) {
				int timeOfStartLecture = 9+i; 
				lectures.add(new Lecture(LocalTime.of(timeOfStartLecture, 0, 0), courses.get(i), classRooms.get(freeRooms.get(i)),
						groups.get(j),
						teachers.get(freeTeachers.get(i))));
			}
		}
		return lectures;
	}
	
	public List<ClassRoom> getFreeClassRoom(Date date, LocalTime localTime) {
		
		List<Lecture> lectures =  dailySchedules.getSchedule().get(date);
		List <ClassRoom> occupiedRooms = lectures.stream()
												 .filter(l-> l.getTime().equals(localTime))
												 .map(l->l.getClassRoom())
												 .collect(toList());
		occupiedRooms.stream().forEach(System.out::println);
		System.out.println();
		List<ClassRoom> freeRooms = classRooms.stream()
											  .filter(c->!occupiedRooms.contains(c))
											  .collect(toList());	
		return freeRooms;
	}

	public DailySchedule createDailySchedules() {
		dailySchedules.addSchedual(new GregorianCalendar(2020, 8, 19).getTime(), createLectures(new GregorianCalendar(2020, 8, 19).getTime()));
		dailySchedules.addSchedual(new GregorianCalendar(2020, 8, 20).getTime(), createLectures(new GregorianCalendar(2020, 8, 19).getTime()));
		dailySchedules.addSchedual(new GregorianCalendar(2020, 8, 21).getTime(), createLectures(new GregorianCalendar(2020, 8, 19).getTime()));
		dailySchedules.addSchedual(new GregorianCalendar(2020, 8, 22).getTime(), createLectures(new GregorianCalendar(2020, 8, 19).getTime()));
		dailySchedules.addSchedual(new GregorianCalendar(2020, 8, 23).getTime(), createLectures(new GregorianCalendar(2020, 8, 19).getTime()));
		return dailySchedules;
	}

	public List<ClassRoom> createClassRooms() {
		classRooms.add(new ClassRoom("100", 40));
		classRooms.add(new ClassRoom("101", 50));
		classRooms.add(new ClassRoom("102", 60));
		classRooms.add(new ClassRoom("103", 30));
		classRooms.add(new ClassRoom("200", 20));
		classRooms.add(new ClassRoom("201", 70));
		classRooms.add(new ClassRoom("202", 60));
		classRooms.add(new ClassRoom("203", 50));
		classRooms.add(new ClassRoom("300", 20));
		classRooms.add(new ClassRoom("301", 40));
		classRooms.add(new ClassRoom("302", 80));
		return classRooms;
	}

	public List<Group> createGroups() {
		String[] alphabet = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R",
				"S", "T", "U", "V", "W", "X", "Y", "Z" };
		int sizeAlphabet = alphabet.length - 1;
		for (int i = 0; i < 10; i++) {
			String nameGroup = String.format("%s%s-%d%d", alphabet[random.nextInt(sizeAlphabet)],
					alphabet[random.nextInt(sizeAlphabet)], random.nextInt(10), i);
			groups.add(new Group(nameGroup));
		}
		assignStudentsToGroup();
		return groups;
	}
	
	public void assignStudentsToGroup() {
		AtomicInteger counter = new AtomicInteger(0);
		students.stream().forEach(s-> groups.get((counter.getAndIncrement())%10).addStudent(s));
	}

	public List<Student> createStudents() {
		List<String> firstNames = Arrays.asList("James", "Chloe", "Alex", "Megan", "Charlotte", "Lauren", "William",
				"George", "Jamie", "Scott", "Alice", "Grace", "Chris", "Rosie", "Robert", "Lily", "Joseph", "lydia",
				"Kirsty", "Tyler");

		List<String> lastNames = Arrays.asList("Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller",
				"Wilson", "Moore", "Anderson", "Thomas", "Jackson", "Harris", "Martin", "Robinson", "Clark",
				"Rodriguez", "Lewis", "Parker", "Foster");

		for (int i = 1; i <= 200; i++) {
			students.add(new Student(firstNames.get(random.nextInt(firstNames.size())),
					lastNames.get(random.nextInt(firstNames.size())), new Date(), "email", "sex", "addres",
					createCourses()));
		}
		return students;
	}

	public List<Teacher> createTeachers() {
		List<String> firstNames = Arrays.asList("James", "Chloe", "Alex", "Megan", "Charlotte", "Lauren", "William",
				"George", "Jamie", "Scott", "Alice", "Grace", "Chris", "Rosie", "Robert", "Lily", "Joseph", "lydia",
				"Kirsty", "Tyler");

		List<String> lastNames = Arrays.asList("Smith", "Johnson", "Williams", "Jones", "Brown", "Davis", "Miller",
				"Wilson", "Moore", "Anderson", "Thomas", "Jackson", "Harris", "Martin", "Robinson", "Clark",
				"Rodriguez", "Lewis", "Parker", "Foster");

		for (int i = 1; i <= 10; i++) {
			teachers.add(new Teacher(firstNames.get(random.nextInt(firstNames.size())),
					lastNames.get(random.nextInt(firstNames.size())), new Date(), "email", "sex", "addres",
					createCourses()));
		}
		return teachers;
	}

	public List<Course> createCourses() {
		courses.add(new Course("Mathematics",
				"Includes the study of such topics as quantity (number theory),structure (algebra), space (geometry), and change (mathematical analysis). It has no generally accepted definition."));
		courses.add(new Course("Biology",
				" Natural science that studies life and living organisms, including their physical structure, chemical processes, molecular interactions, physiological mechanisms, development and evolution."));
		courses.add(new Course("Chemistry",
				"The scientific discipline involved with elements and compounds composed of atoms, molecules and ions: their composition, structure, properties, behavior and the changes they undergo during a reaction with other substances."));
		courses.add(new Course("Physics",
				"Natural science that studies matter, its motion and behavior through space and time, and the related entities of energy and force."));
		courses.add(new Course("History",
				"The study of the past. Events occurring before the invention of writing systems are considered prehistory."));
		courses.add(new Course("Geography",
				" A field of science devoted to the study of the lands, features, inhabitants, and phenomena of the Earth and planets."));
		courses.add(new Course("Astronomy", "Natural science that studies celestial objects and phenomena"));
		courses.add(new Course("Foreign language", "A language originally from another country than the speaker."));
		courses.add(new Course("Informatics",
				" Applies the principles of information science to solve problems using data. It involves the practice of information processing and the engineering of information systems."));
		courses.add(new Course("Economics",
				"The social science that studies the production, distribution, and consumption of goods and services"));
		return courses;
	}
}