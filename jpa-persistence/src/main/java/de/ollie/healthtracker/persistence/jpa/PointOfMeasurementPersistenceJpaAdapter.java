package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.PointOfMeasurement;
import de.ollie.healthtracker.core.service.port.persistence.PointOfMeasurementPersistencePort;
import de.ollie.healthtracker.persistence.jpa.dbo.PointOfMeasurementDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.PointOfMeasurementDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.PointOfMeasurementDboRepository;
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
class PointOfMeasurementPersistenceJpaAdapter implements PointOfMeasurementPersistencePort {

	private final DboFactory dboFactory;
	private final PointOfMeasurementDboMapper mapper;
	private final PointOfMeasurementDboRepository repository;

	@Override
	public PointOfMeasurement create(String name, BigDecimal regularMaxCelsius, BigDecimal regularMinCelsius) {
		return mapper.toModel(
			repository.save(dboFactory.createPointOfMeasurement(name, regularMaxCelsius, regularMinCelsius))
		);
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<PointOfMeasurement> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public Optional<PointOfMeasurement> findByIdOrNameParticle(String nameParticleOrId) {
		ensure(nameParticleOrId != null, "name particle or id cannot be null");
		Optional<PointOfMeasurementDbo> dbo = Optional.empty();
		try {
			UUID uuid = UUID.fromString(nameParticleOrId);
			dbo = repository.findById(uuid);
		} catch (Exception e) {
			// NOP
		}
		return Optional.ofNullable(
			mapper.toModel(
				dbo.orElseGet(() -> {
					List<PointOfMeasurementDbo> found = repository.findAllByNameMatch(nameParticleOrId);
					if (found.size() < 2) {
						return found.size() == 0 ? null : found.get(0);
					}
					throw new TooManyElementsException();
				})
			)
		);
	}

	@Override
	public List<PointOfMeasurement> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public PointOfMeasurement update(PointOfMeasurement toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
