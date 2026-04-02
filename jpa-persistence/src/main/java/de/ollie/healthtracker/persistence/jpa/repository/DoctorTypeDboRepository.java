package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.DoctorTypeDbo;
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
public interface DoctorTypeDboRepository extends JpaRepository<DoctorTypeDbo, UUID> {
	@Query("SELECT dbo FROM DoctorTypeDbo dbo ORDER BY dbo.name")
	List<DoctorTypeDbo> findAllOrdered();

	@Query("SELECT dbo FROM DoctorTypeDbo dbo WHERE dbo.name LIKE CONCAT('%', :name, '%')")
	List<DoctorTypeDbo> findAllByNameMatch(String name);
}
