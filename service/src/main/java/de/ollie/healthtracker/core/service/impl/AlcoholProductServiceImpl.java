package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.AlcoholProductService;
import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import de.ollie.healthtracker.core.service.port.persistence.AlcoholProductPersistencePort;
import jakarta.inject.Named;
import java.math.BigDecimal;
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
class AlcoholProductServiceImpl implements AlcoholProductService {

	private final AlcoholProductPersistencePort alcoholProductPersistencePort;

	@Override
	public AlcoholProduct createAlcoholProduct(String name, BigDecimal percentVol) {
		return alcoholProductPersistencePort.create(name, percentVol);
	}

	@Override
	public void deleteAlcoholProduct(UUID id) {
		alcoholProductPersistencePort.deleteById(id);
	}

	@Override
	public Optional<AlcoholProduct> findById(UUID id) {
		return alcoholProductPersistencePort.findById(id);
	}

	@Override
	public Optional<AlcoholProduct> findByIdOrNameParticle(String namePartOrId) {
		return alcoholProductPersistencePort.findByIdOrNameParticle(namePartOrId);
	}

	@Override
	public List<AlcoholProduct> listAlcoholProducts() {
		return alcoholProductPersistencePort.list();
	}

	@Override
	public AlcoholProduct updateAlcoholProduct(AlcoholProduct toSave) {
		return alcoholProductPersistencePort.update(toSave);
	}
}
