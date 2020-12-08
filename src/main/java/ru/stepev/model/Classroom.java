package ru.stepev.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Classroom {

	private int id;
	private String address;
	private int capacity;
	
	public Classroom(int id) {
		this.id = id;
	}
	
	
	public Classroom(String address, int capacity) {
		this.address = address;
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "ClassRoom [id=" + id + ", address=" + address + ", capacity=" + capacity + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((address == null) ? 0 : address.hashCode());
		result = prime * result + capacity;
		result = prime * result + id;
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
		Classroom other = (Classroom) obj;
		if (address == null) {
			if (other.address != null)
				return false;
		} else if (!address.equals(other.address))
			return false;
		if (capacity != other.capacity)
			return false;
		if (id != other.id)
			return false;
		return true;
	}
}
