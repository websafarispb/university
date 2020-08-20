package ru.stepev.model;

import java.util.*;

public class DailySchedule {

	private Map<Date, List<Lecture>> schedule = new HashMap<>();

	public void addSchedual(Date date, List<Lecture> lectures) {
		schedule.put(date, lectures);
	}

	public Map<Date, List<Lecture>> getSchedule() {
		return schedule;
	}

	public void setSchedule(Map<Date, List<Lecture>> schedule) {
		this.schedule = schedule;
	}

	@Override
	public String toString() {
		return "DailySchedule [schedule=" + schedule + "]";
	}
	
	
}
