package ru.stepev.exception;

public class FildsNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FildsNotFoundException(String message) {
		super(message);
	}
}
