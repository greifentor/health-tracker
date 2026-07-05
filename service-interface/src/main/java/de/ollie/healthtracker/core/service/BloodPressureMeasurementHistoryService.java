package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import java.util.List;

/** Reads blood pressure measurements recorded within the last given number of days. */
public interface BloodPressureMeasurementHistoryService {
	List<BloodPressureMeasurement> findAllOfLastDays(int days);
}
