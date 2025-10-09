package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.MeatConsumptionDbo;
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
public interface MeatConsumptionDboRepository extends JpaRepository<MeatConsumptionDbo, UUID> {
	@Query("SELECT dbo FROM MeatConsumptionDbo dbo ORDER BY dbo.dateOfRecording")
	List<MeatConsumptionDbo> findAllOrdered();

	@Query("SELECT dbo FROM MeatConsumptionDbo dbo WHERE dbo.description LIKE CONCAT('%', :description, '%')")
	List<MeatConsumptionDbo> findAllByDescriptionMatch(String description);
}
