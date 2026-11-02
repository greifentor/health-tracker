package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.MedicationProductDbo;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public interface MedicationProductDboRepository extends JpaRepository<MedicationProductDbo, UUID> {
	@Query("SELECT dbo FROM MedicationProductDbo dbo ORDER BY dbo.expirationDate ASC")
	List<MedicationProductDbo> findAllOrdered();
}
