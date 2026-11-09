package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.LongTermSymptomService;
import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.LongTermSymptom;
import de.ollie.healthtracker.core.service.port.persistence.LongTermSymptomPersistencePort;
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
class LongTermSymptomServiceImpl implements LongTermSymptomService {

	private final LongTermSymptomPersistencePort longTermSymptomPersistencePort;

	@Override
	public LongTermSymptom createLongTermSymptom(
		LocalDate startDate,
		LocalDate endDate,
		BodyPart bodyPart,
		String description,
		boolean unverified
	) {
		return longTermSymptomPersistencePort.create(startDate, endDate, bodyPart, description, unverified);
	}

	@Override
	public void deleteLongTermSymptom(UUID id) {
		longTermSymptomPersistencePort.deleteById(id);
	}

	@Override
	public Optional<LongTermSymptom> findById(UUID id) {
		return longTermSymptomPersistencePort.findById(id);
	}

	@Override
	public Optional<LongTermSymptom> findByIdOrDescriptionParticle(String namePartOrId) {
		return longTermSymptomPersistencePort.findByIdOrDescriptionParticle(namePartOrId);
	}

	@Override
	public List<LongTermSymptom> listLongTermSymptoms() {
		return longTermSymptomPersistencePort.list();
	}

	@Override
	public LongTermSymptom updateLongTermSymptom(LongTermSymptom toSave) {
		return longTermSymptomPersistencePort.update(toSave);
	}
}
