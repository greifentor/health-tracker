package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.MeatProduct;
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
public interface MeatProductService {
	MeatProduct createMeatProduct(int amountInGr, String description, MeatType meatType);

	void deleteMeatProduct(UUID id);

	Optional<MeatProduct> findById(UUID id);

	Optional<MeatProduct> findByIdOrDescriptionParticle(String namePartOrId);

	List<MeatProduct> listMeatProducts();

	MeatProduct updateMeatProduct(MeatProduct toSave);
}
