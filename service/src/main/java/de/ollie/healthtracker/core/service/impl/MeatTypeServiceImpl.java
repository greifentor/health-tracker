package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.MeatTypeService;
import de.ollie.healthtracker.core.service.model.MeatType;
import de.ollie.healthtracker.core.service.port.persistence.MeatTypePersistencePort;
import jakarta.inject.Named;
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
class MeatTypeServiceImpl implements MeatTypeService {

	private final MeatTypePersistencePort meatTypePersistencePort;

	@Override
	public MeatType createMeatType(String name) {
		return meatTypePersistencePort.create(name);
	}

	@Override
	public void deleteMeatType(UUID id) {
		meatTypePersistencePort.deleteById(id);
	}

	@Override
	public Optional<MeatType> findById(UUID id) {
		return meatTypePersistencePort.findById(id);
	}

	@Override
	public Optional<MeatType> findByIdOrNameParticle(String namePartOrId) {
		return meatTypePersistencePort.findByIdOrNameParticle(namePartOrId);
	}

	@Override
	public List<MeatType> listMeatTypes() {
		return meatTypePersistencePort.list();
	}

	@Override
	public MeatType updateMeatType(MeatType toSave) {
		return meatTypePersistencePort.update(toSave);
	}
}
