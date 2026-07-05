package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.MeatConsumption;
import java.util.List;

/** Reads meat consumptions recorded within the last given number of days. */
public interface MeatConsumptionHistoryService {
	List<MeatConsumption> findAllOfLastDays(int days);
}
