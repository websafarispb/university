package ru.stepev.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class Student {

	private int id;
	private int personalNumber;
	private String firstName;
	private String lastName;
	private LocalDate birthday;
	private String email;
	private Gender gender;
	private String address;
	private List<Course> courses;
	
	public Student(int id) {
		this.id = id;
	}

	public Student(int id, int personalNumber, String firstName, String lastName, LocalDate birthday, String email,
			Gender gender, String address, List<Course> courses) {
		this.id = id;
		this.personalNumber = personalNumber;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
		this.email = email;
		this.gender = gender;
		this.address = address;
		this.courses = courses;
	}

	public String getGender() {
		if(gender==null)
			gender = Gender.valueOf("MALE");
		return gender.toString();
	}

	public void setGender(String gender) {
		if(gender==null)
				gender = "MALE";
		this.gender = Gender.valueOf(gender);
	}

	@Override
	public String toString() {
		return "Student [id=" + id + ", firstName=" + firstName + ", lastName="
				+ lastName + "]";
	}
}
