package ru.stepev.model;

import java.time.LocalTime;

public class Lecture {

	private int id;
	private int dailyScheduleId;
	private LocalTime time;
	private Course course;
	private Classroom classRoom;
	private Group group;
	private Teacher teacher;
	
	public Lecture() {
	}
	
	public Lecture(int id, int dailyScheduleId,  LocalTime time, Course course, Classroom classRoom, Group group,
			Teacher teacher) {
		this.id = id;
		this.dailyScheduleId = dailyScheduleId;
		this.time = time;
		this.course = course;
		this.classRoom = classRoom;
		this.group = group;
		this.teacher = teacher;
	}
	
	public int getDailyScheduleId() {
		return dailyScheduleId;
	}

	public void setDailyScheduleId(int dailyScheduleId) {
		this.dailyScheduleId = dailyScheduleId;
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

	public Classroom getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(Classroom classRoom) {
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
	
	@Override
	public String toString() {
		return "Lecture [id=" + id + ", dailyScheduleId=" + dailyScheduleId + ", time=" + time
				+ ", course=" + course.getName() + ", classRoom=" + classRoom.getAddress() + ", group=" + group + ", teacher=" + teacher.lastName + "]"  + System.lineSeparator();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classRoom == null) ? 0 : classRoom.hashCode());
		result = prime * result + ((course == null) ? 0 : course.hashCode());
		result = prime * result + ((group == null) ? 0 : group.hashCode());
		result = prime * result + id;
		result = prime * result + ((teacher == null) ? 0 : teacher.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Lecture other = (Lecture) obj;
		if (classRoom == null) {
			if (other.classRoom != null)
				return false;
		} else if (!classRoom.equals(other.classRoom))
			return false;
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course))
			return false;
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group))
			return false;
		if (id != other.id)
			return false;
		if (teacher == null) {
			if (other.teacher != null)
				return false;
		} else if (!teacher.equals(other.teacher))
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}
}
