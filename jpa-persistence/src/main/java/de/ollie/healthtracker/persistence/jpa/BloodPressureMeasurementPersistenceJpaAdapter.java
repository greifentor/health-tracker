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
import java.util.Optional;
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
		LocalDate dateOfRecording,
		int diaMmHg,
		int pulsePerMinute,
		int sysMmHg,
		LocalTime timeOfRecording,
		BloodPressureMeasurementStatus status,
		boolean irregularHeartBeat
	) {
		return mapper.toModel(
			repository.save(
				dboFactory.createBloodPressureMeasurement(
					dateOfRecording,
					diaMmHg,
					pulsePerMinute,
					sysMmHg,
					timeOfRecording,
					mapper.toDbo(status),
					irregularHeartBeat
				)
			)
		);
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<BloodPressureMeasurement> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public List<BloodPressureMeasurement> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public BloodPressureMeasurement update(BloodPressureMeasurement toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
