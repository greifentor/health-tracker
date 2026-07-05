package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.WeightMeasurementHistoryService;
import de.ollie.healthtracker.core.service.model.WeightMeasurement;
import de.ollie.healthtracker.core.service.port.persistence.WeightMeasurementHistoryPersistencePort;
import jakarta.inject.Named;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class WeightMeasurementHistoryServiceImpl implements WeightMeasurementHistoryService {

	private final WeightMeasurementHistoryPersistencePort weightMeasurementHistoryPersistencePort;

	@Override
	public List<WeightMeasurement> findAllOfLastDays(int days) {
		return weightMeasurementHistoryPersistencePort.findAllOfLastDays(days);
	}
}
