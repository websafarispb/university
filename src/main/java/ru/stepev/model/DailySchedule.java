package ru.stepev.model;

import static java.util.stream.Collectors.joining;

import java.time.LocalDate;
import java.util.*;

public class DailySchedule {

	private int id;
	private LocalDate date;
	private List<Lecture> lectures;
	
	public DailySchedule(LocalDate date, List<Lecture> lectures) {
		this.date = date;
		this.lectures = lectures;
	}
	
	public DailySchedule(int id, LocalDate date) {
		this.id = id;
		this.date = date;
		}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public List<Lecture> getLectures() {
		return lectures;
	}

	public void setLectures(List<Lecture> lectures) {
		this.lectures = lectures;
	}
	
	public String showAllLectures() {
		return  lectures.stream().map(Lecture::toString).collect(joining(System.lineSeparator()));
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "DailySchedule ID"+ id + " for date = " + date + System.lineSeparator() + " Lectures: " + System.lineSeparator() 
										 +  lectures + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + id;
		result = prime * result + ((lectures == null) ? 0 : lectures.hashCode());
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
		DailySchedule other = (DailySchedule) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (id != other.id)
			return false;
		if (lectures == null) {
			if (other.lectures != null)
				return false;
		} else if (!lectures.equals(other.lectures))
			return false;
		return true;
	}
	
	
}
