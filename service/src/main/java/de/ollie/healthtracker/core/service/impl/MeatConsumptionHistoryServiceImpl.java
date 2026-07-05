package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.MeatConsumptionHistoryService;
import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.core.service.port.persistence.MeatConsumptionHistoryPersistencePort;
import jakarta.inject.Named;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class MeatConsumptionHistoryServiceImpl implements MeatConsumptionHistoryService {

	private final MeatConsumptionHistoryPersistencePort meatConsumptionHistoryPersistencePort;

	@Override
	public List<MeatConsumption> findAllOfLastDays(int days) {
		return meatConsumptionHistoryPersistencePort.findAllOfLastDays(days);
	}
}
