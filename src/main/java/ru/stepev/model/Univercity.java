package ru.stepev.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Univercity {

	private DailySchedule dailySchedules;
	private List<Group> groups;
	private List<Course> courses;
	private List<Teacher> teachers;
	private List<Student> students;
	private List<ClassRoom> classRooms;

	public DailySchedule getTimeTableForStudent(String firstName, String lastName, List<Date> periodOfTime) {
		DailySchedule studentDailySchedules = new DailySchedule();
		Map<Date, List<Lecture>> studentTable = new HashMap<>();
		Group groupOfStudent = getGroupOfStudent(firstName, lastName);
		for(Date day : periodOfTime) {
			List <Lecture> lectureOfStudent = new ArrayList<>();
			for(Lecture lecture : dailySchedules.getSchedule().get(day)) {
				if(lecture.getGroup().getName().equals(groupOfStudent.getName()))
					lectureOfStudent.add(lecture);
			}
			studentTable.put(day, lectureOfStudent);
		}
		studentDailySchedules.setSchedule(studentTable);
		return studentDailySchedules;
	}
	
	public Group getGroupOfStudent(String firstName, String lastName) {
		Group groupOfStudent = null;
		for(Group group : groups) {
			for(Student student : group.getStudents()) {
				if(student.firstName.equals(firstName)&&student.lastName.equals(lastName)) {
					groupOfStudent = group;
					break;
				}
			}
		}
		return groupOfStudent;
	}

	public DailySchedule getTimeTableForTeacher(String firstName, String lastName, List<Date> periodOfTime) {
		DailySchedule teacherDailySchedules = new DailySchedule();
		Map<Date, List<Lecture>> teacherTable = new HashMap<>();
		for(Date day : periodOfTime) {
			List <Lecture> lectureOfTeacher = new ArrayList<>();
			for(Lecture lecture : dailySchedules.getSchedule().get(day)) {
				if(lecture.getTeacher().firstName.equals(firstName)&&lecture.getTeacher().lastName.equals(lastName))
					lectureOfTeacher.add(lecture);
			}
			teacherTable.put(day, lectureOfTeacher);
		}
		teacherDailySchedules.setSchedule(teacherTable);
		return teacherDailySchedules;
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
		this.dailySchedules = dailySchedule;
	}
	
	public void assignStudentToGroup(Student student, Group group) {
		group.addStudent(student);
	
	}

	public DailySchedule getDailySchedules() {
		return dailySchedules;
	}

	public void setDailySchedules(DailySchedule dailySchedules) {
		this.dailySchedules = dailySchedules;
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
