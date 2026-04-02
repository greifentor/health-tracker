package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.Exercise;
import de.ollie.healthtracker.core.service.model.BodyPart;
import java.util.List;
import java.util.Optional;
import lombok.Generated;

import java.util.UUID;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public interface ExerciseService {

	Exercise createExercise(BodyPart bodyPart, String description, String name);

	void deleteExercise(UUID id);
	
	Optional<Exercise> findById(UUID id);

	List<Exercise> listExercises();

	Exercise updateExercise(Exercise toSave);
}
