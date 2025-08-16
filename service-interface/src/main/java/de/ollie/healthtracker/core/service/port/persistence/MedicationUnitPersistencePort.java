package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.MedicationUnit;
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
public interface MedicationUnitPersistencePort {
	MedicationUnit create(String name, String token);

	void deleteById(UUID id);

	Optional<MedicationUnit> findById(UUID id);

	Optional<MedicationUnit> findByIdOrNameParticle(String name);

	List<MedicationUnit> list();

	MedicationUnit update(MedicationUnit toSave);
}
