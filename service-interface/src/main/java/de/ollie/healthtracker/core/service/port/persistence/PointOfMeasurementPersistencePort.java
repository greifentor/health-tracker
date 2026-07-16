package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.PointOfMeasurement;
import jakarta.inject.Named;
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
@Named
public interface PointOfMeasurementPersistencePort {
	PointOfMeasurement create(String name, BigDecimal regularMaxCelsius, BigDecimal regularMinCelsius);

	void deleteById(UUID id);

	Optional<PointOfMeasurement> findById(UUID id);

	Optional<PointOfMeasurement> findByIdOrNameParticle(String name);

	List<PointOfMeasurement> list();

	PointOfMeasurement update(PointOfMeasurement toSave);
}
