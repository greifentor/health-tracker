package de.ollie.healthtracker.core.service;

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
public interface GeneralBodyPartService {
	GeneralBodyPart createGeneralBodyPart(String name);

	void deleteGeneralBodyPart(UUID id);

	Optional<GeneralBodyPart> findById(UUID id);

	Optional<GeneralBodyPart> findByIdOrNameParticle(String namePartOrId);

	List<GeneralBodyPart> listGeneralBodyParts();

	GeneralBodyPart updateGeneralBodyPart(GeneralBodyPart toSave);
}
