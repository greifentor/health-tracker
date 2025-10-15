package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.Symptom;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SymptomService {
	Symptom createSymptom(String description, LocalDate dateOfRecording, BodyPart bodyPart);

	void deleteSymptom(UUID id);

	int duplicateNewestSymptomEntries();

	Optional<Symptom> findById(UUID id);

	Optional<Symptom> findByIdOrDescriptionParticle(String namePartOrId);

	List<Symptom> listSymptoms();

	Symptom updateSymptom(Symptom toSave);
}
