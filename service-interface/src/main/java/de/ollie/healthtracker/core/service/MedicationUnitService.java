package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.MedicationUnit;
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
public interface MedicationUnitService {
	MedicationUnit createMedicationUnit(String name, String token);

	void deleteMedicationUnit(UUID id);

	Optional<MedicationUnit> findByIdOrTokenParticle(String token);

	List<MedicationUnit> listMedicationUnits();
}
