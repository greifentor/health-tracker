package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementService;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.WhoBloodPressureClassification;
import de.ollie.healthtracker.core.service.port.persistence.BloodPressureMeasurementPersistencePort;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class BloodPressureMeasurementServiceImpl implements BloodPressureMeasurementService {

	private final BloodPressureMeasurementPersistencePort bloodPressureMeasurementPersistencePort;
	private final BloodPressureMeasurementPrettier bloodPressureMeasurementPrettier;

	@Override
	public BloodPressureMeasurement createBloodPressureMeasurement(
		String comment,
		LocalDate dateOfRecording,
		int diaMmHg,
		int pulsePerMinute,
		int sysMmHg,
		LocalTime timeOfRecording,
		WhoBloodPressureClassification status,
		boolean irregularHeartbeat
	) {
		return bloodPressureMeasurementPersistencePort.create(
			comment,
			dateOfRecording,
			diaMmHg,
			pulsePerMinute,
			sysMmHg,
			timeOfRecording,
			status,
			irregularHeartbeat
		);
	}

	@Override
	public void deleteBloodPressureMeasurement(UUID id) {
		bloodPressureMeasurementPersistencePort.deleteById(id);
	}

	@Override
	public List<BloodPressureMeasurement> findAllBloodPressureMeasurementsPrettifiedByTimeInterval(
		LocalDate from,
		LocalDate to
	) {
		List<BloodPressureMeasurement> bpms = bloodPressureMeasurementPersistencePort.findAllByTimeInterval(from, to);
		return bloodPressureMeasurementPrettier.prettify(bpms);
	}

	@Override
	public Optional<BloodPressureMeasurement> findById(UUID id) {
		return bloodPressureMeasurementPersistencePort.findById(id);
	}

	@Override
	public List<BloodPressureMeasurement> listBloodPressureMeasurements() {
		return bloodPressureMeasurementPersistencePort.list();
	}

	@Override
	public BloodPressureMeasurement updateBloodPressureMeasurement(BloodPressureMeasurement toSave) {
		return bloodPressureMeasurementPersistencePort.update(toSave);
	}
}
