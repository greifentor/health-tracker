package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import java.math.BigDecimal;
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
public interface AlcoholProductService {
	AlcoholProduct createAlcoholProduct(String name, BigDecimal percentVol, BigDecimal liter);

	void deleteAlcoholProduct(UUID id);

	Optional<AlcoholProduct> findById(UUID id);

	Optional<AlcoholProduct> findByIdOrNameParticle(String namePartOrId);

	List<AlcoholProduct> listAlcoholProducts();

	AlcoholProduct updateAlcoholProduct(AlcoholProduct toSave);
}
