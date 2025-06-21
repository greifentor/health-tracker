package de.ollie.bptracker.persistence.jpa.mapper;

import de.ollie.bptracker.core.service.model.BloodPressureMeasurement;
import de.ollie.bptracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.bptracker.persistence.jpa.dbo.BloodPressureMeasurementDbo;
import de.ollie.bptracker.persistence.jpa.dbo.BloodPressureMeasurementStatusDbo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BloodPressureMeasurementDboMapper {
	BloodPressureMeasurementStatusDbo toDbo(BloodPressureMeasurementStatus model);

	BloodPressureMeasurement toModel(BloodPressureMeasurementDbo dbo);
}
