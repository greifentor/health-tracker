package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.DoctorDbo;
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
public interface DoctorDboRepository extends JpaRepository<DoctorDbo, UUID> {
	@Query("SELECT dbo FROM DoctorDbo dbo ORDER BY dbo.name")
	List<DoctorDbo> findAllOrdered();

	@Query("SELECT dbo FROM DoctorDbo dbo WHERE dbo.name LIKE CONCAT('%', :name, '%')")
	List<DoctorDbo> findAllByNameMatch(String name);
}
