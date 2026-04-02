package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.MedicationPlanDbo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Repository
public interface MedicationPlanDboRepository extends JpaRepository<MedicationPlanDbo, UUID> {
	@Query("SELECT dbo FROM MedicationPlanDbo dbo ORDER BY dbo.nextDateOfIntake DESC, dbo.timeOfIntake DESC")
	List<MedicationPlanDbo> findAllOrdered();
}
