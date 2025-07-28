package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.Manufacturer;
import de.ollie.healthtracker.core.service.model.Medication;
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
public interface MedicationService {
	Medication createMedication(String name, Manufacturer manufacturer);

	void deleteMedication(UUID id);

	Optional<Medication> findByIdOrNameParticle(String namePartOrId);

	List<Medication> listMedications();
}
