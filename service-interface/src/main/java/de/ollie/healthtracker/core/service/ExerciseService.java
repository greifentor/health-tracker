package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.Exercise;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public interface ExerciseService {
	Exercise createExercise(String name, String description);

	void deleteExercise(UUID id);

	Optional<Exercise> findById(UUID id);

	Optional<Exercise> findByIdOrNameParticle(String namePartOrId);

	List<Exercise> listExercises();

	Exercise updateExercise(Exercise toSave);
}
