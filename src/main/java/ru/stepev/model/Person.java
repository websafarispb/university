package ru.stepev.model;

import java.time.LocalDate;
import java.util.*;

public class Person {

	protected int id;
	protected int personalNumber;
	protected String firstName;
	protected String lastName;
	protected LocalDate birthday;
	protected String email;
	protected Gender gender;
	protected String address;
	protected List<Course> courses;

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public LocalDate getBirthday() {
		return birthday;
	}

	public void setBirthday(LocalDate birthday) {
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getGender() {
		return gender.toString();
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	public void setGender(String gender) {
		this.gender = Gender.valueOf(gender);
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAddres(String address) {
		this.address = address;
	}

	public List<Course> getCourses() {
		return courses;
	}

	public void setCourses(List<Course> courses) {
		this.courses = courses;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPersonalNumber() {
		return personalNumber;
	}

	public void setPersonalNumber(int personalNumber) {
		this.personalNumber = personalNumber;
	}
}
