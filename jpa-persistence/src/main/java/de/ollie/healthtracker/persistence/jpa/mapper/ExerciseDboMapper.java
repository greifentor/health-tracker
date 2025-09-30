package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.Exercise;
import de.ollie.healthtracker.persistence.jpa.dbo.ExerciseDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface ExerciseDboMapper {
	Exercise toModel(ExerciseDbo dbo);

	ExerciseDbo toDbo(Exercise model);
}
