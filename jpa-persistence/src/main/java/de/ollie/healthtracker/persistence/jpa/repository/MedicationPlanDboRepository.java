package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.MedicationPlanDbo;
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
public interface MedicationPlanDboRepository extends JpaRepository<MedicationPlanDbo, UUID> {
	@Query("SELECT dbo FROM MedicationPlanDbo dbo ORDER BY dbo.nextDateOfIntake DESC, dbo.timeOfIntake DESC")
	List<MedicationPlanDbo> findAllOrdered();
}
