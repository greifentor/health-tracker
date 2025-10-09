package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.core.service.port.persistence.MeatConsumptionPersistencePort;
import de.ollie.healthtracker.persistence.jpa.dbo.MeatConsumptionDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.MeatConsumptionDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.MeatConsumptionDboRepository;
import jakarta.inject.Named;
import java.time.LocalDate;
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
class MeatConsumptionPersistenceJpaAdapter implements MeatConsumptionPersistencePort {

	private final DboFactory dboFactory;
	private final MeatConsumptionDboMapper mapper;
	private final MeatConsumptionDboRepository repository;

	@Override
	public MeatConsumption create(LocalDate dateOfRecording, String description) {
		return mapper.toModel(repository.save(dboFactory.createMeatConsumption(dateOfRecording, description)));
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<MeatConsumption> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public Optional<MeatConsumption> findByIdOrDescriptionParticle(String nameParticleOrId) {
		ensure(nameParticleOrId != null, "name particle or id cannot be null");
		Optional<MeatConsumptionDbo> dbo = Optional.empty();
		try {
			UUID uuid = UUID.fromString(nameParticleOrId);
			dbo = repository.findById(uuid);
		} catch (Exception e) {
			// NOP
		}
		return Optional.ofNullable(
			mapper.toModel(
				dbo.orElseGet(() -> {
					List<MeatConsumptionDbo> found = repository.findAllByDescriptionMatch(nameParticleOrId);
					if (found.size() < 2) {
						return found.size() == 0 ? null : found.get(0);
					}
					throw new TooManyElementsException();
				})
			)
		);
	}

	@Override
	public List<MeatConsumption> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public MeatConsumption update(MeatConsumption toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
