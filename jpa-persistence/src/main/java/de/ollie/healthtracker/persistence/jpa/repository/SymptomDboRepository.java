package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.SymptomDbo;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface SymptomDboRepository extends JpaRepository<SymptomDbo, UUID> {
	@Query("SELECT dbo FROM SymptomDbo dbo ORDER BY dbo.dateOfRecording DESC, dbo.description")
	List<SymptomDbo> findAllOrdered();

	@Query("SELECT dbo FROM SymptomDbo dbo WHERE dbo.description LIKE CONCAT('%', :description, '%')")
	List<SymptomDbo> findAllByDescriptionMatch(String description);

	List<SymptomDbo> findAllByDateOfRecording(LocalDate dateOfRecording);

	@Query("SELECT max(dbo.dateOfRecording) FROM SymptomDbo dbo")
	LocalDate getMaxDateOfRecording();
}
