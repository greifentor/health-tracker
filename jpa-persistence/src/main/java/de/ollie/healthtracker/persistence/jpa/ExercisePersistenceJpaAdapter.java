package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.Exercise;
import de.ollie.healthtracker.core.service.port.persistence.ExercisePersistencePort;
import de.ollie.healthtracker.persistence.jpa.dbo.ExerciseDbo;
import de.ollie.healthtracker.persistence.jpa.mapper.ExerciseDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.ExerciseDboRepository;
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
class ExercisePersistenceJpaAdapter implements ExercisePersistencePort {

	private final DboFactory dboFactory;
	private final ExerciseDboMapper mapper;
	private final ExerciseDboRepository repository;

	@Override
	public Exercise create(String name, String description) {
		return mapper.toModel(repository.save(dboFactory.createExercise(name, description)));
	}

	@Override
	public void deleteById(UUID id) {
		ensure(id != null, "id cannot be null!");
		repository.deleteById(id);
	}

	@Override
	public Optional<Exercise> findById(UUID id) {
		ensure(id != null, "id cannot be null!");
		return repository.findById(id).map(mapper::toModel);
	}

	@Override
	public Optional<Exercise> findByIdOrNameParticle(String nameParticleOrId) {
		ensure(nameParticleOrId != null, "name particle or id cannot be null");
		Optional<ExerciseDbo> dbo = Optional.empty();
		try {
			UUID uuid = UUID.fromString(nameParticleOrId);
			dbo = repository.findById(uuid);
		} catch (Exception e) {
			// NOP
		}
		return Optional.ofNullable(
			mapper.toModel(
				dbo.orElseGet(() -> {
					List<ExerciseDbo> found = repository.findAllByNameMatch(nameParticleOrId);
					if (found.size() < 2) {
						return found.size() == 0 ? null : found.get(0);
					}
					throw new TooManyElementsException();
				})
			)
		);
	}

	@Override
	public List<Exercise> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public Exercise update(Exercise toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
