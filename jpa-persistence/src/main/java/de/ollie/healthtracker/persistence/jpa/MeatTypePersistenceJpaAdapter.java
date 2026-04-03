package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.MeatType;
import de.ollie.healthtracker.core.service.port.persistence.MeatTypePersistencePort;
import de.ollie.healthtracker.persistence.jpa.dbo.MeatTypeDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.MeatTypeDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.MeatTypeDboRepository;
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
class MeatTypePersistenceJpaAdapter implements MeatTypePersistencePort {

	private final DboFactory dboFactory;
	private final MeatTypeDboMapper mapper;
	private final MeatTypeDboRepository repository;

	@Override
	public MeatType create(String name) {
		return mapper.toModel(repository.save(dboFactory.createMeatType(name)));
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<MeatType> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public Optional<MeatType> findByIdOrNameParticle(String nameParticleOrId) {
		ensure(nameParticleOrId != null, "name particle or id cannot be null");
		Optional<MeatTypeDbo> dbo = Optional.empty();
		try {
			UUID uuid = UUID.fromString(nameParticleOrId);
			dbo = repository.findById(uuid);
		} catch (Exception e) {
			// NOP
		}
		return Optional.ofNullable(
			mapper.toModel(
				dbo.orElseGet(() -> {
					List<MeatTypeDbo> found = repository.findAllByNameMatch(nameParticleOrId);
					if (found.size() < 2) {
						return found.size() == 0 ? null : found.get(0);
					}
					throw new TooManyElementsException();
				})
			)
		);
	}

	@Override
	public List<MeatType> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public MeatType update(MeatType toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
