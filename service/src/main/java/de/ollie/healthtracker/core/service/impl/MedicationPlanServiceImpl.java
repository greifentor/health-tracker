package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.MedicationPlanService;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationPlan;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.core.service.port.persistence.MedicationPlanPersistencePort;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.Generated;
import lombok.RequiredArgsConstructor;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
@Named
@RequiredArgsConstructor
class MedicationPlanServiceImpl implements MedicationPlanService {

	private final MedicationPlanPersistencePort medicationPlanPersistencePort;

	@Override
	public MedicationPlan createMedicationPlan(
		LocalDate endDate,
		Medication medication,
		MedicationUnit medicationUnit,
		LocalDate nextDateOfIntake,
		boolean selfMedication,
		LocalDate startDate,
		LocalTime timeOfIntake,
		BigDecimal unitCount
	) {
		return medicationPlanPersistencePort.create(
			endDate,
			medication,
			medicationUnit,
			nextDateOfIntake,
			selfMedication,
			startDate,
			timeOfIntake,
			unitCount
		);
	}

	@Override
	public void deleteMedicationPlan(UUID id) {
		medicationPlanPersistencePort.deleteById(id);
	}

	@Override
	public Optional<MedicationPlan> findById(UUID id) {
		return medicationPlanPersistencePort.findById(id);
	}

	@Override
	public List<MedicationPlan> listMedicationPlans() {
		return medicationPlanPersistencePort.list();
	}

	@Override
	public MedicationPlan updateMedicationPlan(MedicationPlan toSave) {
		return medicationPlanPersistencePort.update(toSave);
	}
}
