package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.AlcoholType;
import de.ollie.healthtracker.core.service.port.persistence.AlcoholTypePersistencePort;
import de.ollie.healthtracker.persistence.jpa.dbo.AlcoholTypeDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.AlcoholTypeDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.AlcoholTypeDboRepository;
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
class AlcoholTypePersistenceJpaAdapter implements AlcoholTypePersistencePort {

	private final DboFactory dboFactory;
	private final AlcoholTypeDboMapper mapper;
	private final AlcoholTypeDboRepository repository;

	@Override
	public AlcoholType create(String name) {
		return mapper.toModel(repository.save(dboFactory.createAlcoholType(name)));
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<AlcoholType> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public Optional<AlcoholType> findByIdOrNameParticle(String nameParticleOrId) {
		ensure(nameParticleOrId != null, "name particle or id cannot be null");
		Optional<AlcoholTypeDbo> dbo = Optional.empty();
		try {
			UUID uuid = UUID.fromString(nameParticleOrId);
			dbo = repository.findById(uuid);
		} catch (Exception e) {
			// NOP
		}
		return Optional.ofNullable(
			mapper.toModel(
				dbo.orElseGet(() -> {
					List<AlcoholTypeDbo> found = repository.findAllByNameMatch(nameParticleOrId);
					if (found.size() < 2) {
						return found.size() == 0 ? null : found.get(0);
					}
					throw new TooManyElementsException("More than one element found for name containing: " + nameParticleOrId);
				})
			)
		);
	}

	@Override
	public List<AlcoholType> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public AlcoholType update(AlcoholType toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
