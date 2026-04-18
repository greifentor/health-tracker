package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.WeightMeasurement;
import de.ollie.healthtracker.core.service.port.persistence.WeightMeasurementPersistencePort;
import de.ollie.healthtracker.persistence.jpa.mapper.WeightMeasurementDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.WeightMeasurementDboRepository;
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
class WeightMeasurementPersistenceJpaAdapter implements WeightMeasurementPersistencePort {

	private final DboFactory dboFactory;
	private final WeightMeasurementDboMapper mapper;
	private final WeightMeasurementDboRepository repository;

	@Override
	public WeightMeasurement create(String comment, LocalDate dateOfRecording, BigDecimal kg, LocalTime timeOfRecording) {
		return mapper.toModel(
			repository.save(dboFactory.createWeightMeasurement(comment, dateOfRecording, kg, timeOfRecording))
		);
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<WeightMeasurement> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public List<WeightMeasurement> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public WeightMeasurement update(WeightMeasurement toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
