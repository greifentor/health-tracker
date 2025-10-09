package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.MeatConsumptionService;
import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.core.service.port.persistence.MeatConsumptionPersistencePort;
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
class MeatConsumptionServiceImpl implements MeatConsumptionService {

	private final MeatConsumptionPersistencePort meatConsumptionPersistencePort;

	@Override
	public MeatConsumption createMeatConsumption(LocalDate dateOfRecording, String description) {
		return meatConsumptionPersistencePort.create(dateOfRecording, description);
	}

	@Override
	public void deleteMeatConsumption(UUID id) {
		meatConsumptionPersistencePort.deleteById(id);
	}

	@Override
	public Optional<MeatConsumption> findById(UUID id) {
		return meatConsumptionPersistencePort.findById(id);
	}

	@Override
	public Optional<MeatConsumption> findByIdOrDescriptionParticle(String namePartOrId) {
		return meatConsumptionPersistencePort.findByIdOrDescriptionParticle(namePartOrId);
	}

	@Override
	public List<MeatConsumption> listMeatConsumptions() {
		return meatConsumptionPersistencePort.list();
	}

	@Override
	public MeatConsumption updateMeatConsumption(MeatConsumption toSave) {
		return meatConsumptionPersistencePort.update(toSave);
	}
}
