package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.MeatProduct;
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
public interface MeatProductPersistencePort {
	MeatProduct create(int amountInGr, String description, MeatType meatType);

	void deleteById(UUID id);

	Optional<MeatProduct> findById(UUID id);

	Optional<MeatProduct> findByIdOrDescriptionParticle(String description);

	List<MeatProduct> list();

	MeatProduct update(MeatProduct toSave);
}
