package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.BodyTemperatureMeasurement;
import jakarta.inject.Named;
import java.math.BigDecimal;
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
public interface BodyTemperatureMeasurementPersistencePort {
	BodyTemperatureMeasurement create(
		String comment,
		LocalDate dateOfRecording,
		BigDecimal celsius,
		LocalTime timeOfRecording
	);

	void deleteById(UUID id);

	Optional<BodyTemperatureMeasurement> findById(UUID id);

	List<BodyTemperatureMeasurement> list();

	BodyTemperatureMeasurement update(BodyTemperatureMeasurement toSave);
}
