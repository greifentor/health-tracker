package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.ExerciseService;
import de.ollie.healthtracker.core.service.model.Exercise;
import de.ollie.healthtracker.core.service.port.persistence.ExercisePersistencePort;
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
class ExerciseServiceImpl implements ExerciseService {

	private final ExercisePersistencePort exercisePersistencePort;

	@Override
	public Exercise createExercise(String name, String description) {
		return exercisePersistencePort.create(name, description);
	}

	@Override
	public void deleteExercise(UUID id) {
		exercisePersistencePort.deleteById(id);
	}

	@Override
	public Optional<Exercise> findById(UUID id) {
		return exercisePersistencePort.findById(id);
	}

	@Override
	public Optional<Exercise> findByIdOrNameParticle(String namePartOrId) {
		return exercisePersistencePort.findByIdOrNameParticle(namePartOrId);
	}

	@Override
	public List<Exercise> listExercises() {
		return exercisePersistencePort.list();
	}

	@Override
	public Exercise updateExercise(Exercise toSave) {
		return exercisePersistencePort.update(toSave);
	}
}
