package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.WhoBloodPressureClassification;
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
		WhoBloodPressureClassification status,
		boolean irregularHeartbeat
	);

	void deleteBloodPressureMeasurement(UUID id);

	Optional<BloodPressureMeasurement> findById(UUID id);

	boolean isDuplicate(
		LocalDate dateOfRecording,
		int diaMmHg,
		int pulsePerMinute,
		int sysMmHg,
		LocalTime timeOfRecording,
		WhoBloodPressureClassification status
	);

	List<BloodPressureMeasurement> listBloodPressureMeasurements();

	BloodPressureMeasurement updateBloodPressureMeasurement(BloodPressureMeasurement toSave);

	List<BloodPressureMeasurement> findAllBloodPressureMeasurementsByTimeInterval(LocalDate from, LocalDate to);

	List<BloodPressureMeasurement> findAllBloodPressureMeasurementsPrettifiedByTimeInterval(LocalDate from, LocalDate to);
}
