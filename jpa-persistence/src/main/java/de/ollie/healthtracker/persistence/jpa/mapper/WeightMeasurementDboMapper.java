package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.WeightMeasurement;
import de.ollie.healthtracker.persistence.jpa.dbo.WeightMeasurementDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface WeightMeasurementDboMapper {
	WeightMeasurement toModel(WeightMeasurementDbo dbo);

	WeightMeasurementDbo toDbo(WeightMeasurement model);
}
