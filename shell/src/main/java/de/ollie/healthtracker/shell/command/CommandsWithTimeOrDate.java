package de.ollie.healthtracker.shell.command;

import de.ollie.baselib.util.DateTimeUtil;
import de.ollie.healthtracker.core.service.LocalDateFactory;
import de.ollie.healthtracker.core.service.LocalTimeFactory;
import java.time.LocalDate;
import java.time.LocalTime;

interface CommandsWithTimeOrDate {
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
		return DateTimeUtil.dateFromString(dateOfMeasurementStr);
	}

	default LocalTime getTimeFromParameter(String timeOfMeasurementStr) {
		if ((timeOfMeasurementStr == null) || "NOW".equalsIgnoreCase(timeOfMeasurementStr)) {
			return getLocalTimeFactory().now();
		}
		return DateTimeUtil.timeFromString(timeOfMeasurementStr);
	}
}
