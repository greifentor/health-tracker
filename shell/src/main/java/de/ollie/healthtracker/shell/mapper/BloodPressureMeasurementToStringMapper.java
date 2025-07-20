package de.ollie.healthtracker.shell.mapper;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;

public interface BloodPressureMeasurementToStringMapper extends ToStringMapper {
	String getHeadLine();

	String getUnderLine();

	String map(BloodPressureMeasurement bloodPressureMeasurement);
}
