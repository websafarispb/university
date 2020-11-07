package ru.stepev.exception;

public class ClassroomIsNotFreeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ClassroomIsNotFreeException(String message) {
		super(message);
	}
}
