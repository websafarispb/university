package ru.stepev.exception;

public class StudentsNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StudentsNotFoundException(String message) {
		super(message);
	}
}
