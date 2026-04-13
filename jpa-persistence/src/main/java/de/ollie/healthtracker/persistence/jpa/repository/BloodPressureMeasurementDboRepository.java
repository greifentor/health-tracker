package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementDbo;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BloodPressureMeasurementDboRepository extends JpaRepository<BloodPressureMeasurementDbo, UUID> {
	@Query("SELECT dbo FROM BloodPressureMeasurementDbo dbo ORDER BY dbo.dateOfRecording DESC, dbo.timeOfRecording DESC")
	List<BloodPressureMeasurementDbo> findAllOrdered();

	@Query(
		"SELECT dbo FROM BloodPressureMeasurementDbo dbo WHERE dbo.dateOfRecording >= :from AND dbo.dateOfRecording <= :until ORDER BY dbo.dateOfRecording DESC, dbo.timeOfRecording DESC"
	)
	List<BloodPressureMeasurementDbo> findAllBetweenDates(LocalDate from, LocalDate until);
}
