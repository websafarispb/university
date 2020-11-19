package ru.stepev.exception;

public class GroupCanNotStudyCourseException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public GroupCanNotStudyCourseException(String message) {
		super(message);
	}
}
