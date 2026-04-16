package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.MeatConsumption;
import de.ollie.healthtracker.core.service.model.MeatProduct;
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
public interface MeatConsumptionService {
	MeatConsumption createMeatConsumption(LocalDate dateOfRecording, MeatProduct meatProduct);

	void deleteMeatConsumption(UUID id);

	Optional<MeatConsumption> findById(UUID id);

	List<MeatConsumption> listMeatConsumptions();

	MeatConsumption updateMeatConsumption(MeatConsumption toSave);
}
