package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.WeightMeasurementService;
import de.ollie.healthtracker.core.service.model.WeightMeasurement;
import de.ollie.healthtracker.core.service.port.persistence.WeightMeasurementPersistencePort;
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
class WeightMeasurementServiceImpl implements WeightMeasurementService {

	private final WeightMeasurementPersistencePort weightMeasurementPersistencePort;

	@Override
	public WeightMeasurement createWeightMeasurement(
		String comment,
		LocalDate dateOfRecording,
		BigDecimal kg,
		LocalTime timeOfRecording
	) {
		return weightMeasurementPersistencePort.create(comment, dateOfRecording, kg, timeOfRecording);
	}

	@Override
	public void deleteWeightMeasurement(UUID id) {
		weightMeasurementPersistencePort.deleteById(id);
	}

	@Override
	public Optional<WeightMeasurement> findById(UUID id) {
		return weightMeasurementPersistencePort.findById(id);
	}

	@Override
	public List<WeightMeasurement> listWeightMeasurements() {
		return weightMeasurementPersistencePort.list();
	}

	@Override
	public WeightMeasurement updateWeightMeasurement(WeightMeasurement toSave) {
		return weightMeasurementPersistencePort.update(toSave);
	}
}
