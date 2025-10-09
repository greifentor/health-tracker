package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.MeatConsumption;
import jakarta.inject.Named;
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
public interface MeatConsumptionPersistencePort {
	MeatConsumption create(LocalDate dateOfRecording, String description);

	void deleteById(UUID id);

	Optional<MeatConsumption> findById(UUID id);

	Optional<MeatConsumption> findByIdOrDescriptionParticle(String description);

	List<MeatConsumption> list();

	MeatConsumption update(MeatConsumption toSave);
}
