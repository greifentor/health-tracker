package de.ollie.healthtracker.core.service.exception;

public class PrintReportException extends RuntimeException {

	public PrintReportException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
