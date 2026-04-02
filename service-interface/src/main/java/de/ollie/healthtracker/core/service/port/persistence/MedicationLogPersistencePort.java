package de.ollie.healthtracker.core.service.port.persistence;

import de.ollie.healthtracker.core.service.model.MedicationLog;
import de.ollie.healthtracker.core.service.model.Medication;
import de.ollie.healthtracker.core.service.model.MedicationUnit;
import jakarta.inject.Named;
import java.util.List;
import java.util.Optional;
import lombok.Generated;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Generated
@Named
public interface MedicationLogPersistencePort {

	MedicationLog create(boolean confirmed, Medication medication, MedicationUnit medicationUnit, LocalDate dateOfIntake, boolean selfMedication, LocalTime timeOfIntake, BigDecimal unitCount);

	void deleteById(UUID id);

	Optional<MedicationLog> findById(UUID id);

	List<MedicationLog> list();
	
	MedicationLog update(MedicationLog toSave);
}
