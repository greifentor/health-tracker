package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementDbo;
import org.mapstruct.Mapper;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Mapper(componentModel = "spring")
public interface BloodPressureMeasurementDboMapper {
	BloodPressureMeasurement toModel(BloodPressureMeasurementDbo dbo);

	BloodPressureMeasurementDbo toDbo(BloodPressureMeasurement model);
}
