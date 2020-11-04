package ru.stepev.exception;

public class EntityCouldNotBeenDeletedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntityCouldNotBeenDeletedException(String message) {
		super(message);
	}
}
