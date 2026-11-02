package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationProduct;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
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
public interface MedicationProductPersistencePort {
	MedicationProduct create(
		LocalDate expirationDate,
		Medication medication,
		BigDecimal packageSize,
		MedicationUnit medicationUnit
	);

	void deleteById(UUID id);

	Optional<MedicationProduct> findById(UUID id);

	List<MedicationProduct> list();

	MedicationProduct update(MedicationProduct toSave);
}
