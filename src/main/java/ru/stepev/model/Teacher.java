package ru.stepev.model;

import java.util.Date;
import java.util.List;

public class Teacher extends Person{

	public Teacher(String firstName, String lastName, Date birthday, String email, String gender, String addres,
			List<Course> courses) {
		
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.email = email;
		this.gender = gender;
		this.addres = addres;
		this.courses = courses;
	}

	@Override
	public String toString() {
		return "Teacher [firstName=" + firstName + ", lastName=" + lastName + "]";
	}
	
	
}
