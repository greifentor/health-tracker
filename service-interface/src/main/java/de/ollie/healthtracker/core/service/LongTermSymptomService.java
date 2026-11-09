package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.LongTermSymptom;
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
public interface LongTermSymptomService {
	LongTermSymptom createLongTermSymptom(
		LocalDate startDate,
		LocalDate endDate,
		BodyPart bodyPart,
		String description,
		boolean unverified
	);

	void deleteLongTermSymptom(UUID id);

	Optional<LongTermSymptom> findById(UUID id);

	Optional<LongTermSymptom> findByIdOrDescriptionParticle(String namePartOrId);

	List<LongTermSymptom> listLongTermSymptoms();

	LongTermSymptom updateLongTermSymptom(LongTermSymptom toSave);
}
