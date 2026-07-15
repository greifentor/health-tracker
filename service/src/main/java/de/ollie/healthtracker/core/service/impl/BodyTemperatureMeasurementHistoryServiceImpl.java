package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.BodyTemperatureMeasurementHistoryService;
import de.ollie.healthtracker.core.service.model.BodyTemperatureMeasurement;
import de.ollie.healthtracker.core.service.port.persistence.BodyTemperatureMeasurementHistoryPersistencePort;
import jakarta.inject.Named;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class BodyTemperatureMeasurementHistoryServiceImpl implements BodyTemperatureMeasurementHistoryService {

	private final BodyTemperatureMeasurementHistoryPersistencePort bodyTemperatureMeasurementHistoryPersistencePort;

	@Override
	public List<BodyTemperatureMeasurement> findAllOfLastDays(int days) {
		return bodyTemperatureMeasurementHistoryPersistencePort.findAllOfLastDays(days);
	}
}
