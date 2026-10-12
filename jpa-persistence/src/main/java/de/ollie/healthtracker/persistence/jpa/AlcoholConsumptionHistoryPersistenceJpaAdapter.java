package de.ollie.healthtracker.persistence.jpa;

import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import de.ollie.healthtracker.core.service.port.persistence.AlcoholConsumptionHistoryPersistencePort;
import de.ollie.healthtracker.persistence.jpa.mapper.AlcoholConsumptionDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.AlcoholConsumptionDboRepository;
import jakarta.inject.Named;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class AlcoholConsumptionHistoryPersistenceJpaAdapter implements AlcoholConsumptionHistoryPersistencePort {

	private final AlcoholConsumptionDboMapper mapper;
	private final AlcoholConsumptionDboRepository repository;

	@Override
	public List<AlcoholConsumption> findAllOfLastDays(int days) {
		return repository.findAllOfLastDays(days).stream().map(mapper::toModel).toList();
	}
}
