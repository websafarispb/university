package ru.stepev.exception;

public class GroupIsNotFreeException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public GroupIsNotFreeException(String message) {
		super(message);
	}
}
