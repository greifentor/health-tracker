package de.ollie.healthtracker.shell.mapper.impl;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.healthtracker.shell.mapper.BloodPressureMeasurementToStringMapper;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Named
class BloodPressureMeasurementToStringMapperImpl implements BloodPressureMeasurementToStringMapper {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");
	private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
	private static final String LINE_FORMAT = "%10s %5s %3d %3d %3d %-6s";

	@Override
	public String map(BloodPressureMeasurement bloodPressureMeasurement) {
		return bloodPressureMeasurement == null
			? null
			: String.format(
				LINE_FORMAT,
				dateToString(bloodPressureMeasurement.getDateOfRecording()),
				timeToString(bloodPressureMeasurement.getTimeOfRecording()),
				bloodPressureMeasurement.getSysMmHg(),
				bloodPressureMeasurement.getDiaMmHg(),
				bloodPressureMeasurement.getPulsePerMinute(),
				statusToString(bloodPressureMeasurement.getStatus())
			);
	}

	private String dateToString(LocalDate localDate) {
		return localDate == null ? "-" : DATE_FORMATTER.format(localDate);
	}

	private String statusToString(BloodPressureMeasurementStatus status) {
		return status == null ? "-" : status.name();
	}

	private String timeToString(LocalTime localTime) {
		return localTime == null ? "-" : TIME_FORMATTER.format(localTime);
	}
}
