package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.BodyTemperatureMeasurement;
import jakarta.inject.Named;
import java.util.List;

/** Port for reading body temperature measurements recorded within the last given number of days. */
@Named
public interface BodyTemperatureMeasurementHistoryPersistencePort {
	List<BodyTemperatureMeasurement> findAllOfLastDays(int days);
}
