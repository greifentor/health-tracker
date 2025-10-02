package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.GeneralBodyPart;
import de.ollie.healthtracker.core.service.port.persistence.GeneralBodyPartPersistencePort;
import de.ollie.healthtracker.persistence.jpa.dbo.GeneralBodyPartDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.GeneralBodyPartDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.GeneralBodyPartDboRepository;
import jakarta.inject.Named;
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
class GeneralBodyPartPersistenceJpaAdapter implements GeneralBodyPartPersistencePort {

	private final DboFactory dboFactory;
	private final GeneralBodyPartDboMapper mapper;
	private final GeneralBodyPartDboRepository repository;

	@Override
	public GeneralBodyPart create(String name) {
		return mapper.toModel(repository.save(dboFactory.createGeneralBodyPart(name)));
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<GeneralBodyPart> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public Optional<GeneralBodyPart> findByIdOrNameParticle(String nameParticleOrId) {
		ensure(nameParticleOrId != null, "name particle or id cannot be null");
		Optional<GeneralBodyPartDbo> dbo = Optional.empty();
		try {
			UUID uuid = UUID.fromString(nameParticleOrId);
			dbo = repository.findById(uuid);
		} catch (Exception e) {
			// NOP
		}
		return Optional.ofNullable(
			mapper.toModel(
				dbo.orElseGet(() -> {
					List<GeneralBodyPartDbo> found = repository.findAllByNameMatch(nameParticleOrId);
					if (found.size() < 2) {
						return found.size() == 0 ? null : found.get(0);
					}
					throw new TooManyElementsException();
				})
			)
		);
	}

	@Override
	public List<GeneralBodyPart> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public GeneralBodyPart update(GeneralBodyPart toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
