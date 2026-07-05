package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.WeightMeasurement;
import jakarta.inject.Named;
import java.util.List;

/** Port for reading weight measurements recorded within the last given number of days. */
@Named
public interface WeightMeasurementHistoryPersistencePort {
	List<WeightMeasurement> findAllOfLastDays(int days);
}
