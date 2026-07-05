package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.WeightMeasurement;
import java.util.List;

/** Reads weight measurements recorded within the last given number of days. */
public interface WeightMeasurementHistoryService {
	List<WeightMeasurement> findAllOfLastDays(int days);
}
