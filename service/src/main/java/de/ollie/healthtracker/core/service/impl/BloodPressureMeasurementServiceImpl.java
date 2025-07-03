package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.healthtracker.core.service.port.persistence.BloodPressureMeasurementPersistencePort;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
public class BloodPressureMeasurementServiceImpl implements BloodPressureMeasurementService {

	private final BloodPressureMeasurementPersistencePort bloodPressureMeasurementPersistencePort;

	@Override
	public BloodPressureMeasurement createRecording(
		int sysMmHg,
		int diaMmHg,
		int pulsePerMinute,
		BloodPressureMeasurementStatus status,
		LocalDate dateOfRecording,
		LocalTime timeOfRecording
	) {
		return bloodPressureMeasurementPersistencePort.create(
			sysMmHg,
			diaMmHg,
			pulsePerMinute,
			status,
			dateOfRecording,
			timeOfRecording
		);
	}

	@Override
	public void deleteRecording(UUID id) {
		bloodPressureMeasurementPersistencePort.deleteById(id);
	}

	@Override
	public List<BloodPressureMeasurement> listRecordings() {
		return bloodPressureMeasurementPersistencePort.list();
	}
}
