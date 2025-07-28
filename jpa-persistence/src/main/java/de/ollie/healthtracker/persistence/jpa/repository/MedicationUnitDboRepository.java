package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.MedicationUnitDbo;
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
public interface MedicationUnitDboRepository extends JpaRepository<MedicationUnitDbo, UUID> {
	@Query("SELECT dbo FROM MedicationUnitDbo dbo")
	List<MedicationUnitDbo> findAllOrdered();

	@Query("SELECT dbo FROM MedicationUnitDbo dbo WHERE dbo.token LIKE CONCAT('%', :token, '%')")
	List<MedicationUnitDbo> findAllByTokenMatch(String token);
}
