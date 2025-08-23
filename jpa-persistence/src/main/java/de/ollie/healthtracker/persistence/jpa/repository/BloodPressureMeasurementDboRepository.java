package de.ollie.healthtracker.persistence.jpa.repository;

import de.ollie.healthtracker.core.service.model.BloodPressureMeasurementStatus;
import de.ollie.healthtracker.persistence.jpa.dbo.BloodPressureMeasurementDbo;
import java.time.LocalDate;
import java.time.LocalTime;
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
public interface BloodPressureMeasurementDboRepository extends JpaRepository<BloodPressureMeasurementDbo, UUID> {
	@Query("SELECT dbo FROM BloodPressureMeasurementDbo dbo ORDER BY dbo.dateOfRecording, dbo.timeOfRecording")
	List<BloodPressureMeasurementDbo> findAllOrdered();
}
