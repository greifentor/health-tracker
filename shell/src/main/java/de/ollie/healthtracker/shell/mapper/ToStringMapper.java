package de.ollie.healthtracker.shell.mapper;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public interface ToStringMapper {
	static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

	default String dateToString(LocalDate localDate) {
		return localDate == null ? "-" : DATE_FORMATTER.format(localDate);
	}

	default String timeToString(LocalTime localTime) {
		return localTime == null ? "-" : TIME_FORMATTER.format(localTime);
	}
}
