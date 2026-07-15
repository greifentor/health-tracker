package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.BodyTemperatureMeasurement;
import java.util.List;

/** Reads body temperature measurements recorded within the last given number of days. */
public interface BodyTemperatureMeasurementHistoryService {
	List<BodyTemperatureMeasurement> findAllOfLastDays(int days);
}
