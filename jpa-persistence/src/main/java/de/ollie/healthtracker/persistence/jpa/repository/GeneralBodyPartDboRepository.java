package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.GeneralBodyPartDbo;
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
public interface GeneralBodyPartDboRepository extends JpaRepository<GeneralBodyPartDbo, UUID> {
	@Query("SELECT dbo FROM GeneralBodyPartDbo dbo ORDER BY dbo.name")
	List<GeneralBodyPartDbo> findAllOrdered();

	@Query("SELECT dbo FROM GeneralBodyPartDbo dbo WHERE dbo.name LIKE CONCAT('%', :name, '%')")
	List<GeneralBodyPartDbo> findAllByNameMatch(String name);
}
