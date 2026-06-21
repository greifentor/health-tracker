package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.AlcoholConsumption;
import de.ollie.healthtracker.core.service.model.AlcoholProduct;
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
public interface AlcoholConsumptionService {
	AlcoholConsumption createAlcoholConsumption(LocalDate date, AlcoholProduct alcoholProduct, String comment);

	void deleteAlcoholConsumption(UUID id);

	Optional<AlcoholConsumption> findById(UUID id);

	List<AlcoholConsumption> listAlcoholConsumptions();

	AlcoholConsumption updateAlcoholConsumption(AlcoholConsumption toSave);
}
