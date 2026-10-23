package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.AlcoholType;
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
public interface AlcoholTypeService {
	AlcoholType createAlcoholType(String name);

	void deleteAlcoholType(UUID id);

	Optional<AlcoholType> findById(UUID id);

	Optional<AlcoholType> findByIdOrNameParticle(String namePartOrId);

	List<AlcoholType> listAlcoholTypes();

	AlcoholType updateAlcoholType(AlcoholType toSave);
}
