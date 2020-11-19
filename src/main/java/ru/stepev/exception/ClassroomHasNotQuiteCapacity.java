package ru.stepev.exception;

public class ClassroomHasNotQuiteCapacity extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClassroomHasNotQuiteCapacity(String message) {
		super(message);
	}
}
