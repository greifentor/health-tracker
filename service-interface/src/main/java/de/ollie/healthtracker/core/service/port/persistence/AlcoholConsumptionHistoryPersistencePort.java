package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import jakarta.inject.Named;
import java.util.List;

/** Port for reading alcohol consumptions recorded within the last given number of days. */
@Named
public interface AlcoholConsumptionHistoryPersistencePort {
	List<AlcoholConsumption> findAllOfLastDays(int days);
}
