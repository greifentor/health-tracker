package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.AlcoholConsumptionHistoryService;
import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import de.ollie.healthtracker.core.service.port.persistence.AlcoholConsumptionHistoryPersistencePort;
import jakarta.inject.Named;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class AlcoholConsumptionHistoryServiceImpl implements AlcoholConsumptionHistoryService {

	private final AlcoholConsumptionHistoryPersistencePort alcoholConsumptionHistoryPersistencePort;

	@Override
	public List<AlcoholConsumption> findAllOfLastDays(int days) {
		return alcoholConsumptionHistoryPersistencePort.findAllOfLastDays(days);
	}
}
