package de.ollie.healthtracker.shell.mapper;

import de.ollie.healthtracker.core.service.model.BodyTemperatureMeasurement;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public interface BodyTemperatureMeasurementToStringMapper extends ToStringMapper {
	String getHeadLine();

	String getUnderLine();

	String map(BodyTemperatureMeasurement model);
}
