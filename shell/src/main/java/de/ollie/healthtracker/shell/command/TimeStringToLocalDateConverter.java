package de.ollie.healthtracker.shell.command;

import static de.ollie.baselib.util.Check.ensure;

import jakarta.inject.Named;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Named
class TimeStringToLocalDateConverter {

	private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("HH:mm");

	LocalTime convert(String timeString) {
		ensure(timeString != null, "time string cannot be null!");
		return LocalTime.parse(timeString, TIME_FORMAT);
	}
}
