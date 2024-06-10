package com.maif.taskmanagerplus.domain.exception;

public class TaskNotFoundException extends EntityNotFoundException {

	private static final long serialVersionUID = 1L;

	public TaskNotFoundException(String message) {
		super(message);
	}
	
	public TaskNotFoundException(Long taskId) {
		this(String.format("There is no register of the task with a code %d", taskId));
	}
	
}
