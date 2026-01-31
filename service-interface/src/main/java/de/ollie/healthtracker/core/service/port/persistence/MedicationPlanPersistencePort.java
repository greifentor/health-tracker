package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationPlan;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
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
public interface MedicationPlanPersistencePort {
	MedicationPlan create(
		LocalDate endDate,
		Medication medication,
		MedicationUnit medicationUnit,
		LocalDate nextDateOfIntake,
		boolean selfMedication,
		LocalDate startDate,
		LocalTime timeOfIntake,
		BigDecimal unitCount
	);

	void deleteById(UUID id);

	Optional<MedicationPlan> findById(UUID id);

	List<MedicationPlan> list();

	MedicationPlan update(MedicationPlan toSave);
}
