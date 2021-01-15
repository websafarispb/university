package ru.stepev.model;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Lecture {

	private int id;
	private int dailyScheduleId;
	private LocalTime time;
	private Course course;
	private Classroom classRoom;
	private Group group;
	private Teacher teacher;
	
	public Lecture(int id) {
		this.id = id;
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
		} else if (!classRoom.equals(other.classRoom)) {
			return false;
		}
		if (course == null) {
			if (other.course != null)
				return false;
		} else if (!course.equals(other.course)) {
			return false;
		}
		if (group == null) {
			if (other.group != null)
				return false;
		} else if (!group.equals(other.group)) {
			return false;
		}
		if (id != other.id)
			return false;
		if (teacher == null) {
			if (other.teacher != null)
				return false;
		} else if (!teacher.equals(other.teacher)) {
			return false;
		}
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Lecture " + id + "[dailyScheduleId=" + dailyScheduleId + ", time=" + time + ", course=" + course + ", classRoom="
				+ classRoom +  ", teacher=" + teacher + "group " + group + "]" + System.lineSeparator();
	}
}
