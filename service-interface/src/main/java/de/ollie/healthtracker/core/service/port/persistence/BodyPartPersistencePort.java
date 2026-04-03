package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.BodyPart;
import de.ollie.healthtracker.core.service.model.GeneralBodyPart;
import jakarta.inject.Named;
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
public interface BodyPartPersistencePort {
	BodyPart create(GeneralBodyPart generalBodyPart, String name);

	void deleteById(UUID id);

	Optional<BodyPart> findById(UUID id);

	Optional<BodyPart> findByIdOrNameParticle(String name);

	List<BodyPart> list();

	BodyPart update(BodyPart toSave);
}
