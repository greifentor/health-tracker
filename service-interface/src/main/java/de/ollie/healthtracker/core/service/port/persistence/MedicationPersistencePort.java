package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.Manufacturer;
import de.ollie.healthtracker.core.service.model.Medication;
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
public interface MedicationPersistencePort {
	Medication create(String name, Manufacturer manufacturer);

	void deleteById(UUID id);

	Optional<Medication> findByIdOrNameParticle(String name);

	List<Medication> list();
}
