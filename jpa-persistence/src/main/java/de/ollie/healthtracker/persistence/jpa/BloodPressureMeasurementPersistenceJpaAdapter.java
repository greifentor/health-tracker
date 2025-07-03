package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.healthtracker.core.service.port.persistence.BloodPressureMeasurementPersistencePort;
import de.ollie.healthtracker.persistence.jpa.mapper.BloodPressureMeasurementDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.BloodPressureMeasurementDboRepository;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
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

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public List<BloodPressureMeasurement> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}
}
