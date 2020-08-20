package ru.stepev.model;

public class ClassRoom {

	private String addres;
	private int capacity;
	
	public ClassRoom(String addres, int capacity) {
		this.addres = addres;
		this.capacity = capacity;
	}
	
	public String getAddres() {
		return addres;
	}

	public void setAddres(String addres) {
		this.addres = addres;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return "ClassRoom [addres=" + addres + ", capacity=" + capacity + "]";
	}
}
