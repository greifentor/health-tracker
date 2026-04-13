package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.MeatProductDbo;
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
public interface MeatProductDboRepository extends JpaRepository<MeatProductDbo, UUID> {
	@Query("SELECT dbo FROM MeatProductDbo dbo ORDER BY dbo.description DESC")
	List<MeatProductDbo> findAllOrdered();

	@Query("SELECT dbo FROM MeatProductDbo dbo WHERE dbo.description LIKE CONCAT('%', :description, '%')")
	List<MeatProductDbo> findAllByDescriptionMatch(String description);
}
