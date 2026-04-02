package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.BodyPartDbo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

/**
 * GENERATED CODE - DO NOT TOUCH
 *
 * Remove this comment to suspend class from generation process.
 */
@Repository
public interface BodyPartDboRepository extends JpaRepository<BodyPartDbo, UUID> {
	@Query("SELECT dbo FROM BodyPartDbo dbo ORDER BY dbo.name")
	List<BodyPartDbo> findAllOrdered();

	@Query("SELECT dbo FROM BodyPartDbo dbo WHERE dbo.name LIKE CONCAT('%', :name, '%')")
	List<BodyPartDbo> findAllByNameMatch(String name);
}
