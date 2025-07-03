package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public interface BloodPressureMeasurementPersistencePort {
	BloodPressureMeasurement create(
		int sysMmHg,
		int diaMmHg,
		int pulsePerMinute,
		BloodPressureMeasurementStatus state,
		LocalDate dateOfRecording,
		LocalTime timeOfRecording
	);

	void deleteById(UUID id);

	List<BloodPressureMeasurement> list();
}
