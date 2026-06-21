package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import jakarta.inject.Named;
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
@Named
public interface AlcoholProductPersistencePort {
	AlcoholProduct create(String name, BigDecimal percentVol, BigDecimal liter);

	void deleteById(UUID id);

	Optional<AlcoholProduct> findById(UUID id);

	Optional<AlcoholProduct> findByIdOrNameParticle(String name);

	List<AlcoholProduct> list();

	AlcoholProduct update(AlcoholProduct toSave);
}
