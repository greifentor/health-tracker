package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.LongTermSymptomDbo;
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
public interface LongTermSymptomDboRepository extends JpaRepository<LongTermSymptomDbo, UUID> {
	@Query("SELECT dbo FROM LongTermSymptomDbo dbo ORDER BY dbo.startDate DESC")
	List<LongTermSymptomDbo> findAllOrdered();

	@Query("SELECT dbo FROM LongTermSymptomDbo dbo WHERE dbo.description LIKE CONCAT('%', :description, '%')")
	List<LongTermSymptomDbo> findAllByDescriptionMatch(String description);
}
