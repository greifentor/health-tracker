package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.GeneralBodyPart;
import de.ollie.healthtracker.core.service.port.persistence.BodyPartPersistencePort;
import de.ollie.healthtracker.persistence.jpa.dbo.BodyPartDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.BodyPartDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.BodyPartDboRepository;
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
class BodyPartPersistenceJpaAdapter implements BodyPartPersistencePort {

	private final DboFactory dboFactory;
	private final BodyPartDboMapper mapper;
	private final BodyPartDboRepository repository;

	@Override
	public BodyPart create(GeneralBodyPart generalBodyPart, String name) {
		return mapper.toModel(repository.save(dboFactory.createBodyPart(generalBodyPart.getId(), name)));
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<BodyPart> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public Optional<BodyPart> findByIdOrNameParticle(String nameParticleOrId) {
		ensure(nameParticleOrId != null, "name particle or id cannot be null");
		Optional<BodyPartDbo> dbo = Optional.empty();
		try {
			UUID uuid = UUID.fromString(nameParticleOrId);
			dbo = repository.findById(uuid);
		} catch (Exception e) {
			// NOP
		}
		return Optional.ofNullable(
			mapper.toModel(
				dbo.orElseGet(() -> {
					List<BodyPartDbo> found = repository.findAllByNameMatch(nameParticleOrId);
					if (found.size() < 2) {
						return found.size() == 0 ? null : found.get(0);
					}
					throw new TooManyElementsException();
				})
			)
		);
	}

	@Override
	public List<BodyPart> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public BodyPart update(BodyPart toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
