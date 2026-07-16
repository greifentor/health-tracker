package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.BodyTemperatureMeasurement;
import de.ollie.healthtracker.core.service.model.PointOfMeasurement;
import de.ollie.healthtracker.core.service.port.persistence.BodyTemperatureMeasurementPersistencePort;
import de.ollie.healthtracker.persistence.jpa.mapper.BodyTemperatureMeasurementDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.BodyTemperatureMeasurementDboRepository;
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
class BodyTemperatureMeasurementPersistenceJpaAdapter implements BodyTemperatureMeasurementPersistencePort {

	private final DboFactory dboFactory;
	private final BodyTemperatureMeasurementDboMapper mapper;
	private final BodyTemperatureMeasurementDboRepository repository;

	@Override
	public BodyTemperatureMeasurement create(
		String comment,
		LocalDate dateOfRecording,
		BigDecimal celsius,
		LocalTime timeOfRecording,
		PointOfMeasurement pointOfMeasurement
	) {
		return mapper.toModel(
			repository.save(
				dboFactory.createBodyTemperatureMeasurement(
					comment,
					dateOfRecording,
					celsius,
					timeOfRecording,
					pointOfMeasurement.getId()
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
	public Optional<BodyTemperatureMeasurement> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public List<BodyTemperatureMeasurement> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public BodyTemperatureMeasurement update(BodyTemperatureMeasurement toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
