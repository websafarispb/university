package ru.stepev.model;

import java.util.*;

public class Group {

	private String name;
	private List<Student> students;
	
	public Group(String name) {
		this.name = name;
		this.students = new ArrayList<>();
	}

	public List<Student> getStudents() {
		return students;
	}

	public void setStudents(List<Student> students) {
		this.students = students;
	}
	
	public void addStudent(Student student) {
		students.add(student);
	}
	
	public void deleteStudent(Student student) {
		students.remove(student);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Group [name=" + name + "students" + students + "]";
	}
}
