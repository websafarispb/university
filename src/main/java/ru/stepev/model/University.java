package ru.stepev.model;

import static java.util.stream.Collectors.toList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class University {

	private List<DailySchedule> dailySchedules = new ArrayList<>();
	private List<Group> groups;
	private List<Course> courses;
	private List<Teacher> teachers;
	private List<Student> students;
	private List<ClassRoom> classRooms;
	private Random random = new Random();

	public List<DailySchedule> getTimeTableForStudent(String firstName, String lastName, List<LocalDate> periodOfTime) {
		List<DailySchedule> studentDailySchedules = new ArrayList<>();
		Group groupOfStudent = getGroupOfStudent(firstName, lastName);
		for (LocalDate day : periodOfTime) {
			System.out.println(day);
			List<Lecture> lecturesOfStudent = new ArrayList<>();
			for (DailySchedule schedule : dailySchedules) {

				if (schedule.getDate().equals(day)) {
					for (Lecture lecture : schedule.getLectures()) {
						if (lecture.getGroup().getName().equals(groupOfStudent.getName()))
							lecturesOfStudent.add(lecture);
					}
					studentDailySchedules.add(new DailySchedule(day, lecturesOfStudent));
				}
			}
		}
		return studentDailySchedules;
	}

	public Group getGroupOfStudent(String firstName, String lastName) {
		Group groupOfStudent = null;
		for (Group group : groups) {
			for (Student student : group.getStudents()) {
				if (student.firstName.equals(firstName) && student.lastName.equals(lastName)) {
					groupOfStudent = group;
					break;
				}
			}
		}
		return groupOfStudent;
	}

	public List<DailySchedule> getTimeTableForTeacher(String firstName, String lastName, List<LocalDate> periodOfTime) {
		List<DailySchedule> teacherDailySchedules = new ArrayList<>();
		for (LocalDate day : periodOfTime) {
			List<Lecture> lectureOfTeacher = new ArrayList<>();
			for (DailySchedule schedule : dailySchedules) {
				if (schedule.getDate().equals(day)) {
					for (Lecture lecture : schedule.getLectures()) {
						if (lecture.getTeacher().firstName.equals(firstName)
								&& lecture.getTeacher().lastName.equals(lastName))
							lectureOfTeacher.add(lecture);
					}
					teacherDailySchedules.add(new DailySchedule(day, lectureOfTeacher));
				}
			}
		}
		return teacherDailySchedules;
	}
	
	public List<Lecture> createLectures(LocalDate date) {
		List<Lecture> lectures = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			List<Integer> freeRooms = random.ints(0, 10).distinct().limit(10).boxed().collect(toList());
			List<Integer> freeTeachers = random.ints(0, 10).distinct().limit(10).boxed().collect(toList());
			int timeOfStartLecture = 9 + i;
			for (int j = 0; j < groups.size(); j++) {
				Lecture lecture = new Lecture(date,LocalTime.of(timeOfStartLecture, 0, 0), courses.get(i),
						classRooms.get(freeRooms.get(j)), groups.get(j), teachers.get(freeTeachers.get(j)));
				lectures.add(lecture);
			}
		}
		return lectures;
	}

	public List<DailySchedule> createDailySchedules() {
		dailySchedules.add(new DailySchedule(LocalDate.of(2020, 8, 19), createLectures(LocalDate.of(2020, 8, 19))));
		dailySchedules.add(new DailySchedule(LocalDate.of(2020, 8, 20), createLectures(LocalDate.of(2020, 8, 20))));
		dailySchedules.add(new DailySchedule(LocalDate.of(2020, 8, 21), createLectures(LocalDate.of(2020, 8, 21))));
		dailySchedules.add(new DailySchedule(LocalDate.of(2020, 8, 22), createLectures(LocalDate.of(2020, 8, 22))));
		dailySchedules.add(new DailySchedule(LocalDate.of(2020, 8, 23), createLectures(LocalDate.of(2020, 8, 23))));
		return dailySchedules;
	}

	public void addStudent(Student student) {
		students.add(student);
	}

	public void addTeacher(Teacher teacher) {
		teachers.add(teacher);
	}

	public void addGroup(Group group) {
		groups.add(group);
	}

	public void addCourse(Course course) {
		courses.add(course);
	}

	public void addDailySchedule(DailySchedule dailySchedule) {
		this.dailySchedules.add(dailySchedule);
	}

	public void assignStudentToGroup(Student student, Group group) {
		group.addStudent(student);

	}

	public List<DailySchedule> getDailySchedules() {
		return dailySchedules;
	}

	public void setDailySchedules(List<DailySchedule> dailySchedules) {
		this.dailySchedules = dailySchedules;
	}
	
	public Group getGroup(String name) {
		Group group = null;
		for(Group g : groups) {
			if(g.getName().equals(name))
				group = g;
		}
		return group;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public List<Teacher> getTeachers() {
		return teachers;
	}

	public void setTeachers(List<Teacher> teachers) {
		this.teachers = teachers;
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}

	public List<ClassRoom> getClassRooms() {
		return classRooms;
	}

	public void setClassRooms(List<ClassRoom> classRooms) {
		this.classRooms = classRooms;
	}

}
