package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.SymptomService;
import de.ollie.healthtracker.core.service.UuidFactory;
import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.Symptom;
import de.ollie.healthtracker.core.service.port.persistence.SymptomPersistencePort;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

@Named
@RequiredArgsConstructor
class SymptomServiceImpl implements SymptomService {

	private final SymptomPersistencePort symptomPersistencePort;
	private final UuidFactory uuidFactory;

	@Generated
	@Override
	public Symptom createSymptom(String description, LocalDate dateOfRecording, BodyPart bodyPart) {
		return symptomPersistencePort.create(description, dateOfRecording, bodyPart);
	}

	@Generated
	@Override
	public void deleteSymptom(UUID id) {
		symptomPersistencePort.deleteById(id);
	}

	@Override
	public int duplicateNewestSymptomEntries() {
		LocalDate maxDate = symptomPersistencePort.getMaxDateOfRecording();
		return symptomPersistencePort
			.findAllByDateOfRecording(maxDate)
			.stream()
			.map(symptom -> {
				symptomPersistencePort.update(symptom.setId(uuidFactory.create()).setDateOfRecording(maxDate.plusDays(1)));
				return 1;
			})
			.reduce((i0, i1) -> i0 + i1)
			.orElse(0);
	}

	@Generated
	@Override
	public Optional<Symptom> findById(UUID id) {
		return symptomPersistencePort.findById(id);
	}

	@Generated
	@Override
	public Optional<Symptom> findByIdOrDescriptionParticle(String namePartOrId) {
		return symptomPersistencePort.findByIdOrDescriptionParticle(namePartOrId);
	}

	@Generated
	@Override
	public List<Symptom> listSymptoms() {
		return symptomPersistencePort.list();
	}

	@Generated
	@Override
	public Symptom updateSymptom(Symptom toSave) {
		return symptomPersistencePort.update(toSave);
	}
}
