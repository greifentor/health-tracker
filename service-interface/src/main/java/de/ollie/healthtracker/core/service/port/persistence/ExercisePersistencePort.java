package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.Exercise;
import jakarta.inject.Named;
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
@Named
public interface ExercisePersistencePort {
	Exercise create(String name, String description);

	void deleteById(UUID id);

	Optional<Exercise> findById(UUID id);

	Optional<Exercise> findByIdOrNameParticle(String name);

	List<Exercise> list();

	Exercise update(Exercise toSave);
}
