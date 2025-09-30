package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.GeneralBodyPartService;
import de.ollie.healthtracker.core.service.model.GeneralBodyPart;
import de.ollie.healthtracker.core.service.port.persistence.GeneralBodyPartPersistencePort;
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
class GeneralBodyPartServiceImpl implements GeneralBodyPartService {

	private final GeneralBodyPartPersistencePort generalBodyPartPersistencePort;

	@Override
	public GeneralBodyPart createGeneralBodyPart(String name) {
		return generalBodyPartPersistencePort.create(name);
	}

	@Override
	public void deleteGeneralBodyPart(UUID id) {
		generalBodyPartPersistencePort.deleteById(id);
	}

	@Override
	public Optional<GeneralBodyPart> findById(UUID id) {
		return generalBodyPartPersistencePort.findById(id);
	}

	@Override
	public Optional<GeneralBodyPart> findByIdOrNameParticle(String namePartOrId) {
		return generalBodyPartPersistencePort.findByIdOrNameParticle(namePartOrId);
	}

	@Override
	public List<GeneralBodyPart> listGeneralBodyParts() {
		return generalBodyPartPersistencePort.list();
	}

	@Override
	public GeneralBodyPart updateGeneralBodyPart(GeneralBodyPart toSave) {
		return generalBodyPartPersistencePort.update(toSave);
	}
}
