package de.ollie.healthtracker.shell.mapper;

import de.ollie.healthtracker.core.service.model.Exercise;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public interface ExerciseToStringMapper extends ToStringMapper {
	String getHeadLine();

	String getUnderLine();

	String map(Exercise model);
}
