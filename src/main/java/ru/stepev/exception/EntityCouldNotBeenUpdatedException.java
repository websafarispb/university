package ru.stepev.exception;

public class EntityCouldNotBeenUpdatedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityCouldNotBeenUpdatedException(String message) {
		super(message);
	}
}
