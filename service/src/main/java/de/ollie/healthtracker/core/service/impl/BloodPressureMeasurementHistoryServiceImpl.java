package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.BloodPressureMeasurementHistoryService;
import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.port.persistence.BloodPressureMeasurementHistoryPersistencePort;
import jakarta.inject.Named;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class BloodPressureMeasurementHistoryServiceImpl implements BloodPressureMeasurementHistoryService {

	private final BloodPressureMeasurementHistoryPersistencePort bloodPressureMeasurementHistoryPersistencePort;

	@Override
	public List<BloodPressureMeasurement> findAllOfLastDays(int days) {
		return bloodPressureMeasurementHistoryPersistencePort.findAllOfLastDays(days);
	}
}
