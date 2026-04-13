package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.MeatProductService;
import de.ollie.healthtracker.core.service.model.MeatProduct;
import de.ollie.healthtracker.core.service.model.MeatType;
import de.ollie.healthtracker.core.service.port.persistence.MeatProductPersistencePort;
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
class MeatProductServiceImpl implements MeatProductService {

	private final MeatProductPersistencePort meatProductPersistencePort;

	@Override
	public MeatProduct createMeatProduct(int amountInGr, String description, MeatType meatType) {
		return meatProductPersistencePort.create(amountInGr, description, meatType);
	}

	@Override
	public void deleteMeatProduct(UUID id) {
		meatProductPersistencePort.deleteById(id);
	}

	@Override
	public Optional<MeatProduct> findById(UUID id) {
		return meatProductPersistencePort.findById(id);
	}

	@Override
	public Optional<MeatProduct> findByIdOrDescriptionParticle(String namePartOrId) {
		return meatProductPersistencePort.findByIdOrDescriptionParticle(namePartOrId);
	}

	@Override
	public List<MeatProduct> listMeatProducts() {
		return meatProductPersistencePort.list();
	}

	@Override
	public MeatProduct updateMeatProduct(MeatProduct toSave) {
		return meatProductPersistencePort.update(toSave);
	}
}
