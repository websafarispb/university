package ru.stepev.exception;

public class TeacherIsNotFreeException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public TeacherIsNotFreeException(String message) {
		super(message);
	}
}
