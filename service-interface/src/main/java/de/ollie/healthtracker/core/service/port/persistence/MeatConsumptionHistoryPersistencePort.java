package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.MeatConsumption;
import jakarta.inject.Named;
import java.util.List;

/** Port for reading meat consumptions recorded within the last given number of days. */
@Named
public interface MeatConsumptionHistoryPersistencePort {
	List<MeatConsumption> findAllOfLastDays(int days);
}
