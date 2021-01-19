package ru.stepev.model;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group {

	private int id;
	private String name;
	private List<Student> students;
	
	public Group(int id) {
		this.id = id;
	}

	public Group(String name) {
		this.name = name;
		this.students = new ArrayList<>();
	}

	public Group(int id, String name) {
		this.id = id;
		this.name = name;
		this.students = new ArrayList<>();
	}

	@Override
	public String toString() {
		return "Group [id=" + id + "name=" + name +  "students" + students + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((students == null) ? 0 : students.hashCode());
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
		Group other = (Group) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (students == null) {
			if (other.students != null)
				return false;
		} else if (!students.equals(other.students)) 
			return false;
		return true;
	}
}
