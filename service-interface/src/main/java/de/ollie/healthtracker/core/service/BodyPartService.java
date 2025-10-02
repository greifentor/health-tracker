package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.GeneralBodyPart;
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
public interface BodyPartService {
	BodyPart createBodyPart(GeneralBodyPart generalBodyPart, String name);

	void deleteBodyPart(UUID id);

	Optional<BodyPart> findById(UUID id);

	Optional<BodyPart> findByIdOrNameParticle(String namePartOrId);

	List<BodyPart> listBodyParts();

	BodyPart updateBodyPart(BodyPart toSave);
}
