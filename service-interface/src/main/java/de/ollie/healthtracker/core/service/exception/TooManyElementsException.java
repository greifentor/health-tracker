package de.ollie.healthtracker.core.service.exception;

public class TooManyElementsException extends RuntimeException {

	public TooManyElementsException() {
		super();
	}

	public TooManyElementsException(String message) {
		super(message);
	}
}
