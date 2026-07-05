package de.ollie.healthtracker.persistence.jpa;

import de.ollie.healthtracker.core.service.model.WeightMeasurement;
import de.ollie.healthtracker.core.service.port.persistence.WeightMeasurementHistoryPersistencePort;
import de.ollie.healthtracker.persistence.jpa.mapper.WeightMeasurementDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.WeightMeasurementDboRepository;
import jakarta.inject.Named;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class WeightMeasurementHistoryPersistenceJpaAdapter implements WeightMeasurementHistoryPersistencePort {

	private final WeightMeasurementDboMapper mapper;
	private final WeightMeasurementDboRepository repository;

	@Override
	public List<WeightMeasurement> findAllOfLastDays(int days) {
		return repository.findAllOfLastDays(days).stream().map(mapper::toModel).toList();
	}
}
