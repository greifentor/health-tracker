package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.MedicationDbo;
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
public interface MedicationDboRepository extends JpaRepository<MedicationDbo, UUID> {
	@Query("SELECT dbo FROM MedicationDbo dbo")
	List<MedicationDbo> findAllOrdered();

	@Query("SELECT dbo FROM MedicationDbo dbo WHERE dbo.name LIKE CONCAT('%', :name, '%')")
	List<MedicationDbo> findAllByNameMatch(String name);
}
