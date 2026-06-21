package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.AlcoholProductDbo;
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
public interface AlcoholProductDboRepository extends JpaRepository<AlcoholProductDbo, UUID> {
	@Query("SELECT dbo FROM AlcoholProductDbo dbo ORDER BY dbo.name")
	List<AlcoholProductDbo> findAllOrdered();

	@Query("SELECT dbo FROM AlcoholProductDbo dbo WHERE dbo.name LIKE CONCAT('%', :name, '%')")
	List<AlcoholProductDbo> findAllByNameMatch(String name);
}
