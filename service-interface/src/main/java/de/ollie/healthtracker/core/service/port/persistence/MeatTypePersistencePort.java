package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.MeatType;
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
public interface MeatTypePersistencePort {
	MeatType create(String name);

	void deleteById(UUID id);

	Optional<MeatType> findById(UUID id);

	Optional<MeatType> findByIdOrNameParticle(String name);

	List<MeatType> list();

	MeatType update(MeatType toSave);
}
