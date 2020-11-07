package ru.stepev.exception;

public class StudentdsNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StudentdsNotFoundException(String message) {
		super(message);
	}
}
