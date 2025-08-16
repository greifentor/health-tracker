package de.ollie.healthtracker.persistence.jpa.mapper;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementDbo;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementStatusDbo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BloodPressureMeasurementDboMapper {
	BloodPressureMeasurementStatusDbo toDbo(BloodPressureMeasurementStatus model);

	BloodPressureMeasurementDbo toDbo(BloodPressureMeasurement model);

	BloodPressureMeasurement toModel(BloodPressureMeasurementDbo dbo);
}
