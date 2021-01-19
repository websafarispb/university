package ru.stepev.model;

import java.time.LocalDate;
import java.util.List;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
public class Teacher {

	private int id;
	private int personalNumber;
	private String firstName;
	private String lastName;
	private LocalDate birthday;
	private String email;
	private Gender gender;
	private String address;
	private List<Course> courses;

	public Teacher(int id) {
		this.id = id;
	}

	public Teacher(int id, int personalNumber, String firstName, String lastName, LocalDate birthday, String email,
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
		if (gender == null)
			gender = Gender.valueOf("MALE");
		return gender.toString();
	}

	public void setGender(String gender) {
		if (gender == null)
			gender = "MALE";
		this.gender = Gender.valueOf(gender);
	}

	@Override
	public String toString() {
		return "Teacher [id=" + id + ", personalNumber=" + personalNumber + ", firstName=" + firstName + ", lastName="
				+ lastName + "] birthday=" + birthday + ", email=" + email + ", gender=" + gender + ", address="
				+ address + ", courses=" + courses + "]"+ System.lineSeparator();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Teacher other = (Teacher) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (birthday == null) {
			if (other.birthday != null)
				return false;
		} else if (!birthday.equals(other.birthday))
			return false;
		if (courses == null) {
			if (other.courses != null)
				return false;
		} else if (!courses.equals(other.courses))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender != other.gender)
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (personalNumber != other.personalNumber)
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
		result = prime * result + ((courses == null) ? 0 : courses.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((gender == null) ? 0 : gender.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + personalNumber;
		return result;
	}
}
