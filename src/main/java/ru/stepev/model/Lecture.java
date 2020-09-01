package ru.stepev.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Lecture {
	
	private int id;
	private LocalDate date;
	private LocalTime time;
	private Course course;
	private ClassRoom classRoom;
	private Group group;
	private Teacher teacher;
	
	public Lecture(LocalDate date, LocalTime time, Course course, ClassRoom classRoom, Group group, Teacher teacher) {
		this.date = date;
		this.time = time;
		this.course = course;
		this.classRoom = classRoom;
		this.group = group;
		this.teacher = teacher;
	}
	
	public Lecture(int id, LocalDate date, LocalTime time, Course course, ClassRoom classRoom, Group group, Teacher teacher) {
		this.id = id;
		this.date = date;

		this.time = time;
		this.course = course;
		this.classRoom = classRoom;
		this.group = group;
		this.teacher = teacher;
	}

	public Lecture(Course course, ClassRoom classroom, Group group, Teacher teacher) {
		this.course = course;
		this.classRoom = classroom;
		this.group = group;
		this.teacher = teacher;
	}

	public LocalTime getTime() {
		return time;
	}

	public void setTime(LocalTime time) {
		this.time = time;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public ClassRoom getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(ClassRoom classRoom) {
		this.classRoom = classRoom;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return time + " " + course + ", classroom=" + classRoom.getAddress() + ", group=" + group.getName()
				+ ", teacher=" + teacher.getFirstName() + " " + teacher.getLastName() + System.lineSeparator();
	}
	
	
}
