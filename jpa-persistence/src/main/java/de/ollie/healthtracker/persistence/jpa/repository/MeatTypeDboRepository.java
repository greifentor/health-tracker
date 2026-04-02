package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.MeatTypeDbo;
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
public interface MeatTypeDboRepository extends JpaRepository<MeatTypeDbo, UUID> {
	@Query("SELECT dbo FROM MeatTypeDbo dbo ORDER BY dbo.name")
	List<MeatTypeDbo> findAllOrdered();

	@Query("SELECT dbo FROM MeatTypeDbo dbo WHERE dbo.name LIKE CONCAT('%', :name, '%')")
	List<MeatTypeDbo> findAllByNameMatch(String name);
}
