package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import jakarta.inject.Named;
import java.util.List;

/** Port for reading blood pressure measurements recorded within the last given number of days. */
@Named
public interface BloodPressureMeasurementHistoryPersistencePort {
	List<BloodPressureMeasurement> findAllOfLastDays(int days);
}
