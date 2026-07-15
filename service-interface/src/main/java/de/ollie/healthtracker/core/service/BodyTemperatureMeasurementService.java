package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.BodyTemperatureMeasurement;
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
public interface BodyTemperatureMeasurementService {
	BodyTemperatureMeasurement createBodyTemperatureMeasurement(
		String comment,
		LocalDate dateOfRecording,
		BigDecimal celsius,
		LocalTime timeOfRecording
	);

	void deleteBodyTemperatureMeasurement(UUID id);

	Optional<BodyTemperatureMeasurement> findById(UUID id);

	List<BodyTemperatureMeasurement> listBodyTemperatureMeasurements();

	BodyTemperatureMeasurement updateBodyTemperatureMeasurement(BodyTemperatureMeasurement toSave);
}
