package ru.stepev.model;

import java.time.LocalTime;

public class Lecture {
	
	private LocalTime time;
	private Course course;
	private ClassRoom classRoom;
	private Group group;
	private Teacher teacher;
	
	public Lecture(LocalTime time, Course course, ClassRoom classRoom, Group group, Teacher teacher) {
		this.time = time;
		this.course = course;
		this.classRoom = classRoom;
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

	@Override
	public String toString() {
		return "Lecture [time=" + time + ", course=" + course + ", classRoom=" + classRoom + ", group=" + group.getName()
				+ ", teacher=" + teacher + "]";
	}
	
	
}
