package de.ollie.healthtracker.core.service;

import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationLog;
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
public interface MedicationLogService {
	MedicationLog createMedicationLog(
		Medication medication,
		MedicationUnit medicationUnit,
		LocalDate dateOfIntake,
		boolean selfMedication,
		LocalTime timeOfIntake,
		BigDecimal unitCount
	);

	void deleteMedicationLog(UUID id);

	Optional<MedicationLog> findById(UUID id);

	List<MedicationLog> listMedicationLogs();

	MedicationLog updateMedicationLog(MedicationLog toSave);
}
