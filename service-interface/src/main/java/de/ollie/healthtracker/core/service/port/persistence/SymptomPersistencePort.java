package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.Symptom;
import jakarta.inject.Named;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;

public interface SymptomPersistencePort {
	Symptom create(String description, LocalDate dateOfRecording, BodyPart bodyPart);

	void deleteById(UUID id);

	List<Symptom> findAllByDateOfRecording(LocalDate dateOfRecording);

	Optional<Symptom> findById(UUID id);

	Optional<Symptom> findByIdOrDescriptionParticle(String description);

	LocalDate getMaxDateOfRecording();

	List<Symptom> list();

	Symptom update(Symptom toSave);
}
