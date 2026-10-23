package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.AlcoholTypeService;
import de.ollie.healthtracker.core.service.model.AlcoholType;
import de.ollie.healthtracker.core.service.port.persistence.AlcoholTypePersistencePort;
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
class AlcoholTypeServiceImpl implements AlcoholTypeService {

	private final AlcoholTypePersistencePort alcoholTypePersistencePort;

	@Override
	public AlcoholType createAlcoholType(String name) {
		return alcoholTypePersistencePort.create(name);
	}

	@Override
	public void deleteAlcoholType(UUID id) {
		alcoholTypePersistencePort.deleteById(id);
	}

	@Override
	public Optional<AlcoholType> findById(UUID id) {
		return alcoholTypePersistencePort.findById(id);
	}

	@Override
	public Optional<AlcoholType> findByIdOrNameParticle(String namePartOrId) {
		return alcoholTypePersistencePort.findByIdOrNameParticle(namePartOrId);
	}

	@Override
	public List<AlcoholType> listAlcoholTypes() {
		return alcoholTypePersistencePort.list();
	}

	@Override
	public AlcoholType updateAlcoholType(AlcoholType toSave) {
		return alcoholTypePersistencePort.update(toSave);
	}
}
