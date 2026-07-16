package de.ollie.healthtracker.shell.mapper;

import de.ollie.healthtracker.core.service.model.PointOfMeasurement;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public interface PointOfMeasurementToStringMapper extends ToStringMapper {
	String getHeadLine();

	String getUnderLine();

	String map(PointOfMeasurement model);
}
