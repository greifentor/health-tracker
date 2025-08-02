package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.Symptom;
import jakarta.inject.Named;
import java.time.LocalDate;
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
@Named
public interface SymptomPersistencePort {
	Symptom create(String description, LocalDate dateOfRecording);

	void deleteById(UUID id);

	Optional<Symptom> findByIdOrDescriptionParticle(String description);

	List<Symptom> list();
}
