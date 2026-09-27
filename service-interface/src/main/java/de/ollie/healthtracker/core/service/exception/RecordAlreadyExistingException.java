package de.ollie.healthtracker.core.service.exception;

public class RecordAlreadyExistingException extends RuntimeException {

	public RecordAlreadyExistingException(String message) {
		super(message);
	}
}
