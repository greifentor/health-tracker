package de.ollie.healthtracker.persistence.jpa;

import de.ollie.healthtracker.core.service.model.BodyTemperatureMeasurement;
import de.ollie.healthtracker.core.service.port.persistence.BodyTemperatureMeasurementHistoryPersistencePort;
import de.ollie.healthtracker.persistence.jpa.mapper.BodyTemperatureMeasurementDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.BodyTemperatureMeasurementDboRepository;
import jakarta.inject.Named;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class BodyTemperatureMeasurementHistoryPersistenceJpaAdapter
	implements BodyTemperatureMeasurementHistoryPersistencePort {

	private final BodyTemperatureMeasurementDboMapper mapper;
	private final BodyTemperatureMeasurementDboRepository repository;

	@Override
	public List<BodyTemperatureMeasurement> findAllOfLastDays(int days) {
		return repository.findAllOfLastDays(days).stream().map(mapper::toModel).toList();
	}
}
