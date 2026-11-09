package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.LongTermSymptom;
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
public interface LongTermSymptomPersistencePort {
	LongTermSymptom create(
		LocalDate startDate,
		LocalDate endDate,
		BodyPart bodyPart,
		String description,
		boolean unverified
	);

	void deleteById(UUID id);

	Optional<LongTermSymptom> findById(UUID id);

	Optional<LongTermSymptom> findByIdOrDescriptionParticle(String description);

	List<LongTermSymptom> list();

	LongTermSymptom update(LongTermSymptom toSave);
}
