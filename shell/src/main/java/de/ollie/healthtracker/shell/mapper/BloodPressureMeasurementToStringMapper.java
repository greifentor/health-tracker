package de.ollie.healthtracker.shell.mapper;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;

public interface BloodPressureMeasurementToStringMapper {
	String map(BloodPressureMeasurement bloodPressureMeasurement);
}
