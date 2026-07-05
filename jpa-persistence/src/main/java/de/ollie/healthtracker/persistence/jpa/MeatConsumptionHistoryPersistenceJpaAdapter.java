package de.ollie.healthtracker.persistence.jpa;

import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.core.service.port.persistence.MeatConsumptionHistoryPersistencePort;
import de.ollie.healthtracker.persistence.jpa.mapper.MeatConsumptionDboMapper;
import de.ollie.healthtracker.persistence.jpa.repository.MeatConsumptionDboRepository;
import jakarta.inject.Named;
import java.util.List;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class MeatConsumptionHistoryPersistenceJpaAdapter implements MeatConsumptionHistoryPersistencePort {

	private final MeatConsumptionDboMapper mapper;
	private final MeatConsumptionDboRepository repository;

	@Override
	public List<MeatConsumption> findAllOfLastDays(int days) {
		return repository.findAllOfLastDays(days).stream().map(mapper::toModel).toList();
	}
}
