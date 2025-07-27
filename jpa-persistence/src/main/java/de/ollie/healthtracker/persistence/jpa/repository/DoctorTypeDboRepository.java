package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.DoctorTypeDbo;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorTypeDboRepository extends JpaRepository<DoctorTypeDbo, UUID> {
	@Query("SELECT c FROM DoctorTypeDbo c ORDER BY c.name")
	List<DoctorTypeDbo> findAllOrdered();

	@Query("SELECT c FROM DoctorTypeDbo c WHERE c.name LIKE CONCAT('%', :name, '%')")
	List<DoctorTypeDbo> findAllByNameMatch(String name);
}
