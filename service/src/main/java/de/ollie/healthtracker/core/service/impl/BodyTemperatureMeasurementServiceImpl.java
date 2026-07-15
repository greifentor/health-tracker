package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.BodyTemperatureMeasurementService;
import de.ollie.healthtracker.core.service.model.BodyTemperatureMeasurement;
import de.ollie.healthtracker.core.service.port.persistence.BodyTemperatureMeasurementPersistencePort;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
class BodyTemperatureMeasurementServiceImpl implements BodyTemperatureMeasurementService {

	private final BodyTemperatureMeasurementPersistencePort bodyTemperatureMeasurementPersistencePort;

	@Override
	public BodyTemperatureMeasurement createBodyTemperatureMeasurement(
		String comment,
		LocalDate dateOfRecording,
		BigDecimal celsius,
		LocalTime timeOfRecording
	) {
		return bodyTemperatureMeasurementPersistencePort.create(comment, dateOfRecording, celsius, timeOfRecording);
	}

	@Override
	public void deleteBodyTemperatureMeasurement(UUID id) {
		bodyTemperatureMeasurementPersistencePort.deleteById(id);
	}

	@Override
	public Optional<BodyTemperatureMeasurement> findById(UUID id) {
		return bodyTemperatureMeasurementPersistencePort.findById(id);
	}

	@Override
	public List<BodyTemperatureMeasurement> listBodyTemperatureMeasurements() {
		return bodyTemperatureMeasurementPersistencePort.list();
	}

	@Override
	public BodyTemperatureMeasurement updateBodyTemperatureMeasurement(BodyTemperatureMeasurement toSave) {
		return bodyTemperatureMeasurementPersistencePort.update(toSave);
	}
}
