package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.MeatType;
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
public interface MeatTypeService {
	MeatType createMeatType(String name);

	void deleteMeatType(UUID id);

	Optional<MeatType> findById(UUID id);

	Optional<MeatType> findByIdOrNameParticle(String namePartOrId);

	List<MeatType> listMeatTypes();

	MeatType updateMeatType(MeatType toSave);
}
