package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import jakarta.inject.Named;
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
@Named
public interface BloodPressureMeasurementPersistencePort {
	BloodPressureMeasurement create(
		LocalDate dateOfRecording,
		int diaMmHg,
		int pulsePerMinute,
		int sysMmHg,
		LocalTime timeOfRecording,
		BloodPressureMeasurementStatus status,
		boolean irregularHeartBeat
	);

	void deleteById(UUID id);

	Optional<BloodPressureMeasurement> findById(UUID id);

	List<BloodPressureMeasurement> list();

	BloodPressureMeasurement update(BloodPressureMeasurement toSave);
}
