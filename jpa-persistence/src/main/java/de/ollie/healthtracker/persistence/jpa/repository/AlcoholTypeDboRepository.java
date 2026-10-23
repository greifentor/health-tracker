package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.AlcoholTypeDbo;
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
public interface AlcoholTypeDboRepository extends JpaRepository<AlcoholTypeDbo, UUID> {
	@Query("SELECT dbo FROM AlcoholTypeDbo dbo ORDER BY dbo.name")
	List<AlcoholTypeDbo> findAllOrdered();

	@Query("SELECT dbo FROM AlcoholTypeDbo dbo WHERE dbo.name LIKE CONCAT('%', :name, '%')")
	List<AlcoholTypeDbo> findAllByNameMatch(String name);
}
