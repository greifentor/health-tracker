package de.ollie.healthtracker.core.service.impl;

import de.ollie.healthtracker.core.service.MedicationLogService;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import de.ollie.healthtracker.core.service.port.persistence.MedicationLogPersistencePort;
import jakarta.inject.Named;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
class MedicationLogServiceImpl implements MedicationLogService {

	private final MedicationLogPersistencePort medicationLogPersistencePort;

	@Override
	public MedicationLog createMedicationLog(
		Medication medication,
		MedicationUnit medicationUnit,
		LocalDate dateOfIntake,
		LocalTime timeOfIntake,
		BigDecimal unitCount
	) {
		return medicationLogPersistencePort.create(medication, medicationUnit, dateOfIntake, timeOfIntake, unitCount);
	}

	@Override
	public void deleteMedicationLog(UUID id) {
		medicationLogPersistencePort.deleteById(id);
	}

	@Override
	public List<MedicationLog> listMedicationLogs() {
		return medicationLogPersistencePort.list();
	}
}
