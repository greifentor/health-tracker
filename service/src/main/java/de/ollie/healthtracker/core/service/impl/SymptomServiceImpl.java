package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.SymptomService;
import de.ollie.healthtracker.core.service.model.Symptom;
import de.ollie.healthtracker.core.service.port.persistence.SymptomPersistencePort;
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
class SymptomServiceImpl implements SymptomService {

	private final SymptomPersistencePort symptomPersistencePort;

	@Override
	public Symptom createSymptom(String description, LocalDate dateOfRecording) {
		return symptomPersistencePort.create(description, dateOfRecording);
	}

	@Override
	public void deleteSymptom(UUID id) {
		symptomPersistencePort.deleteById(id);
	}

	@Override
	public Optional<Symptom> findById(UUID id) {
		return symptomPersistencePort.findById(id);
	}

	@Override
	public Optional<Symptom> findByIdOrDescriptionParticle(String namePartOrId) {
		return symptomPersistencePort.findByIdOrDescriptionParticle(namePartOrId);
	}

	@Override
	public List<Symptom> listSymptoms() {
		return symptomPersistencePort.list();
	}

	@Override
	public Symptom updateSymptom(Symptom toSave) {
		return symptomPersistencePort.update(toSave);
	}
}
