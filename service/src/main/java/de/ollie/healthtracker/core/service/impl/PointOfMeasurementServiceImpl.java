package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.PointOfMeasurementService;
import de.ollie.healthtracker.core.service.model.PointOfMeasurement;
import de.ollie.healthtracker.core.service.port.persistence.PointOfMeasurementPersistencePort;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
@Named
@RequiredArgsConstructor
class PointOfMeasurementServiceImpl implements PointOfMeasurementService {

	private final PointOfMeasurementPersistencePort pointOfMeasurementPersistencePort;

	@Override
	public PointOfMeasurement createPointOfMeasurement(
		String name,
		BigDecimal regularMaxCelsius,
		BigDecimal regularMinCelsius
	) {
		return pointOfMeasurementPersistencePort.create(name, regularMaxCelsius, regularMinCelsius);
	}

	@Override
	public void deletePointOfMeasurement(UUID id) {
		pointOfMeasurementPersistencePort.deleteById(id);
	}

	@Override
	public Optional<PointOfMeasurement> findById(UUID id) {
		return pointOfMeasurementPersistencePort.findById(id);
	}

	@Override
	public Optional<PointOfMeasurement> findByIdOrNameParticle(String namePartOrId) {
		return pointOfMeasurementPersistencePort.findByIdOrNameParticle(namePartOrId);
	}

	@Override
	public List<PointOfMeasurement> listPointOfMeasurements() {
		return pointOfMeasurementPersistencePort.list();
	}

	@Override
	public PointOfMeasurement updatePointOfMeasurement(PointOfMeasurement toSave) {
		return pointOfMeasurementPersistencePort.update(toSave);
	}
}
