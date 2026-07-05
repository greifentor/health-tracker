package de.ollie.healthtracker.persistence.jpa;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurement;
import de.ollie.healthtracker.core.service.port.persistence.BloodPressureMeasurementHistoryPersistencePort;
import de.ollie.healthtracker.persistence.jpa.mapper.BloodPressureMeasurementDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.BloodPressureMeasurementDboRepository;
import jakarta.inject.Named;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class BloodPressureMeasurementHistoryPersistenceJpaAdapter implements BloodPressureMeasurementHistoryPersistencePort {

	private final BloodPressureMeasurementDboMapper mapper;
	private final BloodPressureMeasurementDboRepository repository;

	@Override
	public List<BloodPressureMeasurement> findAllOfLastDays(int days) {
		return repository.findAllOfLastDays(days).stream().map(mapper::toModel).toList();
	}
}
