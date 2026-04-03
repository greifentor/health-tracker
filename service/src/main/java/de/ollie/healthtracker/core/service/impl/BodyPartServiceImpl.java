package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.BodyPartService;
import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.GeneralBodyPart;
import de.ollie.healthtracker.core.service.port.persistence.BodyPartPersistencePort;
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
class BodyPartServiceImpl implements BodyPartService {

	private final BodyPartPersistencePort bodyPartPersistencePort;

	@Override
	public BodyPart createBodyPart(GeneralBodyPart generalBodyPart, String name) {
		return bodyPartPersistencePort.create(generalBodyPart, name);
	}

	@Override
	public void deleteBodyPart(UUID id) {
		bodyPartPersistencePort.deleteById(id);
	}

	@Override
	public Optional<BodyPart> findById(UUID id) {
		return bodyPartPersistencePort.findById(id);
	}

	@Override
	public Optional<BodyPart> findByIdOrNameParticle(String namePartOrId) {
		return bodyPartPersistencePort.findByIdOrNameParticle(namePartOrId);
	}

	@Override
	public List<BodyPart> listBodyParts() {
		return bodyPartPersistencePort.list();
	}

	@Override
	public BodyPart updateBodyPart(BodyPart toSave) {
		return bodyPartPersistencePort.update(toSave);
	}
}
