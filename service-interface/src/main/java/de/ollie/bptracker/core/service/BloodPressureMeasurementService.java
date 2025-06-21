package de.ollie.bptracker.core.service;

import de.ollie.bptracker.core.service.model.BloodPressureMeasurement;
import de.ollie.bptracker.core.service.model.BloodPressureMeasurementStatus;
import java.time.LocalDate;
import java.time.LocalTime;

public interface BloodPressureMeasurementService {
	BloodPressureMeasurement createRecording(
		int sysMmHg,
		int diaMmHg,
		int pulsePerMinute,
		BloodPressureMeasurementStatus state,
		LocalDate dateOfRecording,
		LocalTime timeOfRecording
	);
}
