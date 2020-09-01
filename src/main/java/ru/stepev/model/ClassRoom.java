package ru.stepev.model;

public class ClassRoom {

	private int id;
	private String address;
	private int capacity;
	
	public ClassRoom(String address, int capacity) {
		this.address = address;
		this.capacity = capacity;
	}
	
	public ClassRoom(int id, String address, int capacity) {
		this.id = id;
		this.address = address;
		this.capacity = capacity;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ClassRoom [address=" + address + ", capacity=" + capacity + "]";
	}
}
