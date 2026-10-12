package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import java.util.List;

/** Reads alcohol consumptions recorded within the last given number of days. */
public interface AlcoholConsumptionHistoryService {
	List<AlcoholConsumption> findAllOfLastDays(int days);
}
