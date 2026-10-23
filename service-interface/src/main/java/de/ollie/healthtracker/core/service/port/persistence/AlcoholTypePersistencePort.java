package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.AlcoholType;
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
public interface AlcoholTypePersistencePort {
	AlcoholType create(String name);

	void deleteById(UUID id);

	Optional<AlcoholType> findById(UUID id);

	Optional<AlcoholType> findByIdOrNameParticle(String name);

	List<AlcoholType> list();

	AlcoholType update(AlcoholType toSave);
}
