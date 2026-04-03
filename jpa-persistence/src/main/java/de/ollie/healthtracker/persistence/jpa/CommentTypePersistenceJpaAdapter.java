package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.CommentType;
import de.ollie.healthtracker.core.service.port.persistence.CommentTypePersistencePort;
import de.ollie.healthtracker.persistence.jpa.dbo.CommentTypeDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.CommentTypeDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.CommentTypeDboRepository;
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
class CommentTypePersistenceJpaAdapter implements CommentTypePersistencePort {

	private final DboFactory dboFactory;
	private final CommentTypeDboMapper mapper;
	private final CommentTypeDboRepository repository;

	@Override
	public CommentType create(String name) {
		return mapper.toModel(repository.save(dboFactory.createCommentType(name)));
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<CommentType> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public Optional<CommentType> findByIdOrNameParticle(String nameParticleOrId) {
		ensure(nameParticleOrId != null, "name particle or id cannot be null");
		Optional<CommentTypeDbo> dbo = Optional.empty();
		try {
			UUID uuid = UUID.fromString(nameParticleOrId);
			dbo = repository.findById(uuid);
		} catch (Exception e) {
			// NOP
		}
		return Optional.ofNullable(
			mapper.toModel(
				dbo.orElseGet(() -> {
					List<CommentTypeDbo> found = repository.findAllByNameMatch(nameParticleOrId);
					if (found.size() < 2) {
						return found.size() == 0 ? null : found.get(0);
					}
					throw new TooManyElementsException();
				})
			)
		);
	}

	@Override
	public List<CommentType> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public CommentType update(CommentType toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
