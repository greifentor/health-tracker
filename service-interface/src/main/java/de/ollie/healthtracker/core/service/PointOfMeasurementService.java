package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.PointOfMeasurement;
import java.math.BigDecimal;
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
public interface PointOfMeasurementService {
	PointOfMeasurement createPointOfMeasurement(String name, BigDecimal regularMaxCelsius, BigDecimal regularMinCelsius);

	void deletePointOfMeasurement(UUID id);

	Optional<PointOfMeasurement> findById(UUID id);

	Optional<PointOfMeasurement> findByIdOrNameParticle(String namePartOrId);

	List<PointOfMeasurement> listPointOfMeasurements();

	PointOfMeasurement updatePointOfMeasurement(PointOfMeasurement toSave);
}
