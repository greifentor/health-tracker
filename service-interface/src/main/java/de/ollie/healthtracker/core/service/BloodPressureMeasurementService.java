package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface BloodPressureMeasurementService {
	BloodPressureMeasurement createRecording(
		int sysMmHg,
		int diaMmHg,
		int pulsePerMinute,
		boolean irregularHeartbeat,
		BloodPressureMeasurementStatus state,
		LocalDate dateOfRecording,
		LocalTime timeOfRecording
	);

	void deleteRecording(UUID id);

	List<BloodPressureMeasurement> listRecordings();
}
