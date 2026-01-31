package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationPlan;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
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
public interface MedicationPlanService {
	MedicationPlan createMedicationPlan(
		LocalDate endDate,
		Medication medication,
		MedicationUnit medicationUnit,
		LocalDate nextDateOfIntake,
		boolean selfMedication,
		LocalDate startDate,
		LocalTime timeOfIntake,
		BigDecimal unitCount
	);

	void deleteMedicationPlan(UUID id);

	Optional<MedicationPlan> findById(UUID id);

	List<MedicationPlan> listMedicationPlans();

	MedicationPlan updateMedicationPlan(MedicationPlan toSave);
}
