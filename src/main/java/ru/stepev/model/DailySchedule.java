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
		return "DailySchedule for date = " + date + System.lineSeparator() + " Lectures: " + System.lineSeparator() 
										 +  lectures + "]";
	}
	
	
}
