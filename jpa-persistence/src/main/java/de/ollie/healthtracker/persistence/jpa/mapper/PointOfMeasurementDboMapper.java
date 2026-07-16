package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.PointOfMeasurement;
import de.ollie.healthtracker.persistence.jpa.dbo.PointOfMeasurementDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface PointOfMeasurementDboMapper {
	PointOfMeasurement toModel(PointOfMeasurementDbo dbo);

	PointOfMeasurementDbo toDbo(PointOfMeasurement model);
}
