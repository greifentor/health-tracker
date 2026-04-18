package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.WeightMeasurement;
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
public interface WeightMeasurementPersistencePort {
	WeightMeasurement create(String comment, LocalDate dateOfRecording, BigDecimal kg, LocalTime timeOfRecording);

	void deleteById(UUID id);

	Optional<WeightMeasurement> findById(UUID id);

	List<WeightMeasurement> list();

	WeightMeasurement update(WeightMeasurement toSave);
}
