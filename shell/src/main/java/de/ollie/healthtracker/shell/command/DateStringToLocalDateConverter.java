package de.ollie.healthtracker.shell.command;

import static de.ollie.baselib.util.Check.ensure;

import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Named
class DateStringToLocalDateConverter {

	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	LocalDate convert(String dateString) {
		ensure(dateString != null, "date string cannot be null!");
		return LocalDate.parse(dateString, DATE_FORMAT);
	}
}
