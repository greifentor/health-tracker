package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Named
public interface BloodPressureMeasurementPersistencePort {
	BloodPressureMeasurement create(
		String comment,
		LocalDate dateOfRecording,
		int diaMmHg,
		int pulsePerMinute,
		int sysMmHg,
		LocalTime timeOfRecording,
		BloodPressureMeasurementStatus status,
		boolean irregularHeartbeat
	);

	void deleteById(UUID id);

	List<BloodPressureMeasurement> findAllByTimeInterval(LocalDate from, LocalDate to);

	Optional<BloodPressureMeasurement> findById(UUID id);

	List<BloodPressureMeasurement> list();

	BloodPressureMeasurement update(BloodPressureMeasurement toSave);
}
