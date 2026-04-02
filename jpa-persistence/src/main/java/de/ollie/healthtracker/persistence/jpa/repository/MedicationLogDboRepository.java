package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.MedicationLogDbo;
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
public interface MedicationLogDboRepository extends JpaRepository<MedicationLogDbo, UUID> {
	@Query("SELECT dbo FROM MedicationLogDbo dbo ORDER BY dbo.dateOfIntake DESC, dbo.timeOfIntake DESC")
	List<MedicationLogDbo> findAllOrdered();
}
