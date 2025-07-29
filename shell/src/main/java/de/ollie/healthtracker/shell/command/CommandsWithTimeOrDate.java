package de.ollie.healthtracker.shell.command;

import de.ollie.healthtracker.core.service.LocalDateFactory;
import de.ollie.healthtracker.core.service.LocalTimeFactory;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.Locale;

interface CommandsWithTimeOrDate {
	static final DateTimeFormatter DE_DATE_FORMAT = DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMAN);
	static final DateTimeFormatter DE_TIME_FORMAT = new DateTimeFormatterBuilder()
		.appendValue(ChronoField.HOUR_OF_DAY, 1, 2, java.time.format.SignStyle.NOT_NEGATIVE)
		.appendLiteral(':')
		.appendValue(ChronoField.MINUTE_OF_HOUR, 2)
		.toFormatter();

	LocalDateFactory getLocalDateFactory();

	LocalTimeFactory getLocalTimeFactory();

	default LocalDate getDateFromParameter(String dateOfMeasurementStr) {
		if (
			(dateOfMeasurementStr == null) ||
			"TODAY".equalsIgnoreCase(dateOfMeasurementStr) ||
			"TD".equalsIgnoreCase(dateOfMeasurementStr)
		) {
			return getLocalDateFactory().now();
		}
		return LocalDate.parse(dateOfMeasurementStr, DE_DATE_FORMAT);
	}

	default LocalTime getTimeFromParameter(String timeOfMeasurementStr) {
		if ((timeOfMeasurementStr == null) || "NOW".equalsIgnoreCase(timeOfMeasurementStr)) {
			return getLocalTimeFactory().now();
		}
		return LocalTime.parse(timeOfMeasurementStr, DE_TIME_FORMAT);
	}
}
