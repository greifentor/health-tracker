package de.ollie.healthtracker.persistence.jpa;

import static de.ollie.baselib.util.Check.ensure;

import de.ollie.healthtracker.core.service.exception.TooManyElementsException;
import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.Exercise;
import de.ollie.healthtracker.core.service.port.persistence.ExercisePersistencePort;
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
	public Exercise create(BodyPart bodyPart, String description, String name) {
		return mapper.toModel(repository.save(dboFactory.createExercise(bodyPart.getId(), description, name)));
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
	public List<Exercise> list() {
		return repository.findAllOrdered().stream().map(mapper::toModel).toList();
	}

	@Override
	public Exercise update(Exercise toSave) {
		return mapper.toModel(repository.save(mapper.toDbo(toSave)));
	}
}
