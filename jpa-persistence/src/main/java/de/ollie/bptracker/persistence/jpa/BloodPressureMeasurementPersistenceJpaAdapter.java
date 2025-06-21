package de.ollie.bptracker.persistence.jpa;

import de.ollie.bptracker.core.service.model.BloodPressureMeasurement;
import de.ollie.bptracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.bptracker.core.service.port.persistence.BloodPressureMeasurementPersistencePort;
import de.ollie.bptracker.persistence.jpa.mapper.BloodPressureMeasurementDboMapper;
import de.ollie.bptracker.persistence.jpa.repository.BloodPressureMeasurementDboRepository;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class BloodPressureMeasurementPersistenceJpaAdapter implements BloodPressureMeasurementPersistencePort {

	private final DboFactory dboFactory;
	private final BloodPressureMeasurementDboMapper mapper;
	private final BloodPressureMeasurementDboRepository repository;

	@Override
	public BloodPressureMeasurement create(
		int sysMmHg,
		int diaMmHg,
		int pulsePerMinute,
		BloodPressureMeasurementStatus status,
		LocalDate dateOfRecording,
		LocalTime timeOfRecording
	) {
		return mapper.toModel(
			repository.save(
				dboFactory.create(sysMmHg, diaMmHg, pulsePerMinute, mapper.toDbo(status), dateOfRecording, timeOfRecording)
			)
		);
	}
}
