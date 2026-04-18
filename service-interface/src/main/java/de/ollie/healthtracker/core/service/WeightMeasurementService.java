package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.WeightMeasurement;
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
public interface WeightMeasurementService {
	WeightMeasurement createWeightMeasurement(
		String comment,
		LocalDate dateOfRecording,
		BigDecimal kg,
		LocalTime timeOfRecording
	);

	void deleteWeightMeasurement(UUID id);

	Optional<WeightMeasurement> findById(UUID id);

	List<WeightMeasurement> listWeightMeasurements();

	WeightMeasurement updateWeightMeasurement(WeightMeasurement toSave);
}
