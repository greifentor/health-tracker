package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.ManufacturerDbo;
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
public interface ManufacturerDboRepository extends JpaRepository<ManufacturerDbo, UUID> {
	@Query("SELECT dbo FROM ManufacturerDbo dbo")
	List<ManufacturerDbo> findAllOrdered();

	@Query("SELECT dbo FROM ManufacturerDbo dbo WHERE dbo.name LIKE CONCAT('%', :name, '%')")
	List<ManufacturerDbo> findAllByNameMatch(String name);
}
