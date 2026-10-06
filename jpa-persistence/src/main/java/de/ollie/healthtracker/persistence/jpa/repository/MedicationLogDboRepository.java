package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.MedicationLogDbo;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Repository
public interface MedicationLogDboRepository extends JpaRepository<MedicationLogDbo, UUID> {
	@Query("SELECT dbo FROM MedicationLogDbo dbo ORDER BY dbo.dateOfIntake DESC, dbo.timeOfIntake DESC")
	List<MedicationLogDbo> findAllOrdered();

	@Query(
		"SELECT COUNT(dbo) > 0 FROM MedicationLogDbo dbo WHERE dbo.medication.id = :medication AND dbo.medicationUnit.id = :medicationUnit AND dbo.dateOfIntake = :dateOfIntake AND dbo.timeOfIntake = :timeOfIntake AND dbo.unitCount = :unitCount"
	)
	boolean isDuplicate(
		UUID medication,
		UUID medicationUnit,
		LocalDate dateOfIntake,
		LocalTime timeOfIntake,
		BigDecimal unitCount
	);
}
