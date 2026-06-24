package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import de.ollie.healthtracker.core.service.model.AlcoholProduct;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public interface AlcoholConsumptionPersistencePort {
	AlcoholConsumption create(LocalDate date, AlcoholProduct alcoholProduct, String comment, BigDecimal liter);

	void deleteById(UUID id);

	Optional<AlcoholConsumption> findById(UUID id);

	List<AlcoholConsumption> list();

	AlcoholConsumption update(AlcoholConsumption toSave);
}
