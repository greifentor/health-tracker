package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.PointOfMeasurementDbo;
import java.math.BigDecimal;
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
public interface PointOfMeasurementDboRepository extends JpaRepository<PointOfMeasurementDbo, UUID> {
	@Query("SELECT dbo FROM PointOfMeasurementDbo dbo")
	List<PointOfMeasurementDbo> findAllOrdered();

	@Query("SELECT dbo FROM PointOfMeasurementDbo dbo WHERE dbo.name LIKE CONCAT('%', :name, '%')")
	List<PointOfMeasurementDbo> findAllByNameMatch(String name);
}
