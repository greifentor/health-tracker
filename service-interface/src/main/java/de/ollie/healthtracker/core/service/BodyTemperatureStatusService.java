package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.BodyTemperatureStatus;
import de.ollie.healthtracker.core.service.model.PointOfMeasurement;
import java.math.BigDecimal;

/**
 * Classifies a measured body temperature into a {@link BodyTemperatureStatus}, using the regular range of the given
 * point of measurement (or a default range when none - or no bound - is given).
 */
public interface BodyTemperatureStatusService {
	BodyTemperatureStatus calculateStatus(BigDecimal celsius, PointOfMeasurement pointOfMeasurement);
}
