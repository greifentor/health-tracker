package de.ollie.healthtracker.core.service.exception;

public class FileSystemAccessException extends RuntimeException {

	public FileSystemAccessException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
