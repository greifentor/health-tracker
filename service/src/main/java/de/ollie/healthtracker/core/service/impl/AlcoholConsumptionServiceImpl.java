package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.AlcoholConsumptionService;
import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import de.ollie.healthtracker.core.service.port.persistence.AlcoholConsumptionPersistencePort;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
@Named
@RequiredArgsConstructor
class AlcoholConsumptionServiceImpl implements AlcoholConsumptionService {

	private final AlcoholConsumptionPersistencePort alcoholConsumptionPersistencePort;

	@Override
	public AlcoholConsumption createAlcoholConsumption(LocalDate date, AlcoholProduct alcoholProduct, String comment) {
		return alcoholConsumptionPersistencePort.create(date, alcoholProduct, comment);
	}

	@Override
	public void deleteAlcoholConsumption(UUID id) {
		alcoholConsumptionPersistencePort.deleteById(id);
	}

	@Override
	public Optional<AlcoholConsumption> findById(UUID id) {
		return alcoholConsumptionPersistencePort.findById(id);
	}

	@Override
	public List<AlcoholConsumption> listAlcoholConsumptions() {
		return alcoholConsumptionPersistencePort.list();
	}

	@Override
	public AlcoholConsumption updateAlcoholConsumption(AlcoholConsumption toSave) {
		return alcoholConsumptionPersistencePort.update(toSave);
	}
}
