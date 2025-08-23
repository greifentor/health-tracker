package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public interface BloodPressureMeasurementService {
	BloodPressureMeasurement createBloodPressureMeasurement(
		LocalDate dateOfRecording,
		int diaMmHg,
		int pulsePerMinute,
		int sysMmHg,
		LocalTime timeOfRecording,
		BloodPressureMeasurementStatus status,
		boolean irregularHeartBeat
	);

	void deleteBloodPressureMeasurement(UUID id);

	Optional<BloodPressureMeasurement> findById(UUID id);

	List<BloodPressureMeasurement> listBloodPressureMeasurements();

	BloodPressureMeasurement updateBloodPressureMeasurement(BloodPressureMeasurement toSave);
}
