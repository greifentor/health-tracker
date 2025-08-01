package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.Symptom;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
public interface SymptomService {
	Symptom createSymptom(String description, LocalDate dateOfRecording, LocalTime timeOfRecording);

	void deleteSymptom(UUID id);

	Optional<Symptom> findByIdOrDescriptionParticle(String namePartOrId);

	List<Symptom> listSymptoms();
}
