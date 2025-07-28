package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.DoctorTypeDbo;
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
public interface DoctorTypeDboRepository extends JpaRepository<DoctorTypeDbo, UUID> {
	@Query("SELECT dbo FROM DoctorTypeDbo dbo")
	List<DoctorTypeDbo> findAllOrdered();

	@Query("SELECT dbo FROM DoctorTypeDbo dbo WHERE dbo.name LIKE CONCAT('%', :name, '%')")
	List<DoctorTypeDbo> findAllByNameMatch(String name);
}
