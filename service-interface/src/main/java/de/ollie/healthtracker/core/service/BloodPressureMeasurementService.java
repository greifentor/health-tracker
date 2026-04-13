package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BloodPressureMeasurementService {
	BloodPressureMeasurement createBloodPressureMeasurement(
		String comment,
		LocalDate dateOfRecording,
		int diaMmHg,
		int pulsePerMinute,
		int sysMmHg,
		LocalTime timeOfRecording,
		BloodPressureMeasurementStatus status,
		boolean irregularHeartbeat
	);

	void deleteBloodPressureMeasurement(UUID id);

	List<BloodPressureMeasurement> findAllBloodPressureMeasurementsPrettifiedByTimeInterval(LocalDate from, LocalDate to);

	Optional<BloodPressureMeasurement> findById(UUID id);

	List<BloodPressureMeasurement> listBloodPressureMeasurements();

	BloodPressureMeasurement updateBloodPressureMeasurement(BloodPressureMeasurement toSave);
}
